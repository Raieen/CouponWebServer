package xyz.raieen.couponwebserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

/**
 * REST Controller
 */
@RestController
public class CouponRestController {

    @Autowired
    CouponsRepository couponsRepository;
    Logger logger = LoggerFactory.getLogger(CouponRestController.class);

    /**
     * Creates a coupon if authorized.
     *
     * @param input  coupon to be created.
     * @param secret secret key. Return null if invalid secret
     * @return Returns the new coupon if successful, null otherwise.
     */
    @PutMapping(value = "/coupon/")
    public Coupon createCoupon(@RequestBody Coupon input, @RequestHeader String secret) {
        if (!authorized(secret)) return null;

        Coupon coupon = new Coupon(input.isRedeemable(), input.getAction(), input.getRecipient(), input.getQuantity(), 0);
        Coupon result = couponsRepository.insert(coupon);

        try {
            CouponWebServer.getEmailSender().sendCouponEmail(coupon);
        } catch (MessagingException e) {
            logger.error(String.format("Error sending email for coupon %s\n%s", coupon.getId(), e.getMessage()));
            return null;
        }

        return result;
    }

    // Read Coupon

    /**
     * Returns the coupon given the id if it exists.
     *
     * @param id     Coupon id
     * @param secret secret key. Return null if invalid secret
     * @return Returns coupon if the id is valid
     */
    @PostMapping(value = "/coupon/{id}")
    public Coupon getCoupon(@PathVariable String id, @RequestHeader String secret) {
        if (!authorized(secret)) return null;
        logger.debug(String.format("Returning coupon %s", id));
        return couponsRepository.findById(id).orElse(null);
    }

    /**
     * Redeem a coupon given the id. The coupon's redeemed timestamp is set as current time.
     *
     * @param id     coupon id
     * @param secret secret key. Return null if invalid secret
     * @reutrn Returns coupon if valid with the timestamp set as current time, null otherwise.
     */
    @PostMapping(value = "/coupon/{id}/redeem")
    public Coupon redeemCoupon(@PathVariable String id, @RequestHeader String secret) {
        if (!authorized(secret)) return null;

        Coupon coupon = couponsRepository.findById(id).orElse(null);
        if (coupon == null) {
            logger.debug(String.format("Coupon %s not found.", id));
            return null; // Can't find the coupon
        }

        // Redeem coupon
        coupon.setRedeemed(System.currentTimeMillis());
        logger.debug(String.format("Redeeming coupon %s", id));
        return couponsRepository.save(coupon);
    }

    // TODO: 17/05/19 Debug things, remove these.

    @GetMapping(value = "/coupon/debug")
    public List<Coupon> dumpCoupons() {
        return couponsRepository.findAll();
    }

    @GetMapping(value = "/coupon/create")
    public Coupon debugCreateCoupon() {
        return couponsRepository.insert(new Coupon(true, "High Five", "ryan.sue@raieen.xyz", 1, 0));
    }

    public boolean authorized(String secret) {
        return secret.equals(CouponWebServer.getCouponSecret());
    }
}
