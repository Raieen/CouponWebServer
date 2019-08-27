package xyz.raieen.couponwebserver;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CouponController {

    @Autowired
    CouponsRepository couponsRepository;

    @RequestMapping("/coupon/{id}")
    public String serveCoupon(@PathVariable String id, Model model) {
        Coupon coupon = couponsRepository.findById(id).orElse(null);
        if (coupon == null) { // Coupon not found... error page
            return "error";
        }

        if (coupon.getRedeemed() != 0) {
            return "redeemed"; // Go to the redeemed page.
        }

        model.addAttribute("qrimage", getQRBase64("raieen.xyz/coupon/" + id));
        model.addAttribute("coupon", coupon);

        model.addAttribute("redeemable", coupon.isRedeemable() ? "Redeemable" : "Non-Redeemable");

//        model.addAttribute("redeemable", coupon.isRedeemable());
//        model.addAttribute("action", coupon.getAction());
//        model.addAttribute("quantity", coupon.getQuantity());
//        model.addAttribute("redeemed", coupon.getRedeemed());

        return "coupon";
    }

    public String getQRBase64(String contents) {
        Map<EncodeHintType, Object> map = new HashMap<>();
        map.put(EncodeHintType.QR_VERSION, 10); // "Big" QR
        map.put(EncodeHintType.MARGIN, 0);
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        QRCodeWriter writer = new QRCodeWriter();

        try {
            BitMatrix matrix = writer.encode(contents, BarcodeFormat.QR_CODE, 512, 512, map);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "png", byteArrayOutputStream);
            String base64 = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());

            return base64;
        } catch (WriterException | IOException e) {
//            e.printStackTrace();
            return null;
        }

    }
}
