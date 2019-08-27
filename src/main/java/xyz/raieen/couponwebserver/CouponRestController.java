package xyz.raieen.couponwebserver;

import com.google.api.client.util.Base64;
import com.google.api.services.gmail.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

@RestController
public class CouponRestController {

    @Autowired
    CouponsRepository couponsRepository;

    // CRUD Operations

    // Create
    @PutMapping(value = "/coupon/")
    public Coupon createCoupon(@RequestBody Coupon input, @RequestHeader String secret) {
        if (!authorized(secret)) return null;
        Coupon coupon = new Coupon(input.isRedeemable(), input.getAction(), input.getReceipient(), input.getQuantity(), 0);
        Coupon result = couponsRepository.insert(coupon);

        // TODO: How should I check if an email is valid?
        CouponWebServer.sendEmail(coupon);
        return result;
    }

    // Read
    @PostMapping(value = "/coupon/{id}")
    public Coupon getCoupon(@PathVariable String id, @RequestHeader String secret) {
        if (!authorized(secret)) return null;
        return couponsRepository.findById(id).orElse(null);
    }

    // Redeemed.
    @PostMapping(value = "/coupon/{id}/redeem")
    public Coupon redeemCoupon(@PathVariable String id, @RequestHeader String secret) {
        System.out.println("id = " + id);
        System.out.println("secret = " + secret);
        System.out.println("1");
        if (!authorized(secret)) return null;
        Coupon coupon = couponsRepository.findById(id).orElse(null);
        System.out.println("2");
        if (coupon == null) return null;
        System.out.println("3");
        coupon.setRedeemed(System.currentTimeMillis());
        return couponsRepository.save(coupon);
    }

    // TODO: 17/05/19 Update

    // TODO: 17/05/19 Delete

    // TODO: 17/05/19 Debug things, remove these.

    @GetMapping(value = "/coupon/debug")
    public List<Coupon> dumpCoupons() {
        return couponsRepository.findAll();
    }

    @GetMapping(value = "/coupon/create")
    public Coupon debugCreateCoupon() {
        return couponsRepository.insert(new Coupon(true, "High Five", "ryan.sue@raieen.xyz", 1, 0));
    }

    // TODO: How should I do this?
    public boolean authorized(String secret) {
        return secret.equals("abc");
    }
}
