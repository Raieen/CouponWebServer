package xyz.raieen.couponwebserver;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class CouponController {

    @Autowired
    CouponsRepository couponsRepository;

    @RequestMapping("/coupon/{id}")
    public String serveCoupon(@PathVariable String id, Model model) {
        Coupon coupon = couponsRepository.findById(id).orElse(null);

        // Coupon not found... error page
        if (coupon == null) {
            return "error";
        }

        // Coupon has been redeemed
        if (coupon.getRedeemed() != 0) {
            return "redeemed";
        }

        try {
            model.addAttribute("qrimage", CouponUtils.getCouponQRBase64(id));
        } catch (WriterException | IOException e) {
            // TODO: 2019-08-27 Log it.
            return "error";
        }
        model.addAttribute("coupon", coupon);
        model.addAttribute("redeemable", coupon.isRedeemable() ? "Redeemable" : "Non-Redeemable");
        return "coupon";
    }
}
