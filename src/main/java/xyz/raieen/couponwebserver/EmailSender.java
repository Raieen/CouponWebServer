package xyz.raieen.couponwebserver;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private Session session;
    private String emailSender;

    public EmailSender(Session session, String emailSender) {
        this.session = session;
        this.emailSender = emailSender;
    }

    public void sendCouponEmail(Coupon coupon) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(emailSender);
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(coupon.getReceipient()));
        String redeemStatus = coupon.isRedeemable() ? "Redeemable" : "Non-Redeemable";
        mimeMessage.setSubject(String.format(CouponWebServer.getSubjectFormat(), coupon.getQuantity(), redeemStatus, coupon.getAction()));
        mimeMessage.setText(String.format(CouponWebServer.getBodyFormat(), coupon.getQuantity(), coupon.getAction(), CouponUtils.formatCouponURL(coupon.getId())));
        Transport.send(mimeMessage);
    }
}
