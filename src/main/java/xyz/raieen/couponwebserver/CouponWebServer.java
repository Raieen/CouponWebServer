package xyz.raieen.couponwebserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Component
@Configuration
@SpringBootApplication
public class CouponWebServer {

    static final String COUPON_ROOT = "http://raieen.xyz:8080/coupon/";
    public static final String EMAIL_SENDER = "highfivecouponnoreply@gmail.com";
    private static Session session;

    private static EmailSender emailSender;

    public static void main(String[] args) throws Exception {
        // TODO: Put this in some config... don't hardcodre this!!
        String pass = "ENGwu49fKagXGzZjaFyv9HfNCArg9J7Ahdykq7Dw8p2hUr2Jw79YNJzB9FfxNQ9HXtiKw4tstSKMKW3QiVZfWPDAcT9nfzKkLGeh";

        /*
         * Configuration
         * Not using @Value since injection is done after calling run
         */

        /*
         * Email Setup
         */
        emailSender = new EmailSender(session, "xxx");


        // TODO: 2019-08-27 Read this from a properties file.
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("highfivecouponnoreply@gmail.com", pass);
            }
        });

        /*
         * Spring
         */
        SpringApplication.run(CouponWebServer.class, args);
    }

    public static EmailSender getEmailSender() {
        return emailSender;
    }
}
