package xyz.raieen.couponwebserver;

import com.google.zxing.WriterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * Coupon Controller
 */
@Controller
public class CouponController {

    @Autowired
    CouponsRepository couponsRepository;
    Logger logger = LoggerFactory.getLogger(CouponController.class);

    /**
     * Populates model with the coupon's details if possible.
     * If the coupon does not exist or something went wrong, it serves an error page
     * If the coupon has been redeemed, the redeemed page is served.
     *
     * @param id    Coupon Id
     * @param model Model
     * @return template
     */
    @RequestMapping("/coupon/{id}")
    public String serveCoupon(@PathVariable String id, Model model) {
        Coupon coupon = couponsRepository.findById(id).orElse(null);

        // Coupon not found... error page
        if (coupon == null) {
            logger.debug(String.format("Coupon %s not found.", id));
            return "error";
        }

        // Coupon has been redeemed
        if (coupon.getRedeemed() != 0) {
            logger.debug(String.format("Coupon %s was redeemed.", id));
            return "redeemed";
        }

        try {
            model.addAttribute("qrimage", CouponUtils.getCouponQRBase64(id));
        } catch (WriterException | IOException e) {
            logger.error(String.format("Error generating qr image base64 for coupon %s\n%e", id, e.getMessage()));
            return "error";
        }
        model.addAttribute("coupon", coupon);
        model.addAttribute("redeemable", coupon.isRedeemable() ? "Redeemable" : "Non-Redeemable");
        logger.debug(String.format("Serving coupon %s", id));
        return "coupon";
    }
}
