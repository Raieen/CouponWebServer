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
        mimeMessage.setSubject(String.format("%d %s %s", coupon.getQuantity(),
                coupon.isRedeemable() ? "Redeemable" : "Non-Redeemable", coupon.getAction())); // Eg. 1 Non-Redeemable High Five
        mimeMessage.setText(String.format("Hello. Your coupon (%d %s) can be found here, %s", coupon.getQuantity(), coupon.getAction(), "http://raieen.xyz:8080/coupon/" + coupon.getId()));
        Transport.send(mimeMessage);
    }
}
