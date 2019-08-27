package xyz.raieen.couponwebserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@SpringBootApplication
public class CouponWebServer {

    private static String couponURLFormat;
    private static String couponSecret;
    private static String subjectFormat;
    private static String bodyFormat;
    private static EmailSender emailSender;

    public static void main(String[] args) throws Exception {
        /*
         * Properties
         * Not using @Value since injection is done after calling run
         */
        Properties properties = new Properties();
        properties.load(CouponWebServer.class.getClassLoader().getResourceAsStream("application.properties"));

        couponURLFormat = properties.getProperty("coupon.urlformat");
        couponSecret = properties.getProperty("coupon.secret");
        subjectFormat = properties.getProperty("coupon.email.subject");
        bodyFormat = properties.getProperty("coupon.email.body");

        /*
         * Email Setup
         */
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("mail.sender"),
                        properties.getProperty("mail.password"));
            }
        });

        emailSender = new EmailSender(session, properties.getProperty("mail.sender"));

        /*
         * Spring
         */
        SpringApplication.run(CouponWebServer.class, args);
    }

    public static EmailSender getEmailSender() {
        return emailSender;
    }

    public static String getCouponURLFormat() {
        return couponURLFormat;
    }

    public static String getCouponSecret() {
        return couponSecret;
    }

    public static String getSubjectFormat() {
        return subjectFormat;
    }

    public static String getBodyFormat() {
        return bodyFormat;
    }
}
