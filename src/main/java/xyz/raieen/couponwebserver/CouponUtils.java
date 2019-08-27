package xyz.raieen.couponwebserver;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for coupons.
 */
final class CouponUtils {

    /**
     * Returns a formatted coupon url given the coupon id.
     * eg. 1337 --> http://raieen.xyz/coupons/1337
     *
     * @param couponId coupon id
     * @return Returns the coupon formatted string url with coupon id
     */
    public static String formatCouponURL(String couponId) {
        return String.format(CouponWebServer.getCouponURLFormat(), couponId);
    }

    /**
     * Returns the base64 representation of a qr code of the given contents
     * <p>
     * Purposely overkill use of QR Version 10 due to scanner camera
     * https://www.qrcode.com/en/about/version.html was ignored.
     *
     * @param contents contents in the qr code
     * @return Returns base64 representation of a qr code of contents
     */
    private static String getQRBase64(String contents) throws WriterException, IOException {
        Map<EncodeHintType, Object> map = new HashMap<>();
        map.put(EncodeHintType.QR_VERSION, 10); // "Big" QR
        map.put(EncodeHintType.MARGIN, 0);
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

        // Write it
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(contents, BarcodeFormat.QR_CODE, 512, 512, map);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "png", byteArrayOutputStream);

        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    /**
     * Gets the base64 representation of a qr code for a given coupon id
     *
     * @param couponId coupon id
     * @return Returns base64 representation of a qr code for a given coupon id.
     * @throws WriterException Throws WriterException if something went wrong making the qr code
     * @throws IOException     Throws IOException if something went wrong writing the qr code
     */
    public static String getCouponQRBase64(String couponId) throws WriterException, IOException {
        return getQRBase64(formatCouponURL(couponId));
    }
}
