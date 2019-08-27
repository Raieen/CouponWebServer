package xyz.raieen.couponwebserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@SpringBootApplication
public class CouponWebServer {

    //    private static Gmail gmail;
    public static final String USER = "highfivecouponnoreply@gmail.com";
    private static Session session;


    public static void main(String[] args) throws Exception {
        // TODO: Put this in some config... don't hardcodre this!!
        String pass = "ENGwu49fKagXGzZjaFyv9HfNCArg9J7Ahdykq7Dw8p2hUr2Jw79YNJzB9FfxNQ9HXtiKw4tstSKMKW3QiVZfWPDAcT9nfzKkLGeh";

        // Email Setup
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

//        // Setup Gmail
//        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
//        JsonFactory jsonFactory = new JacksonFactory();
//        final String CREDENTIALS = "credentials.json";
//
//        final String applicationId = "couponwebserver-sender";
//
//        // Credentials
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new FileReader(CREDENTIALS));
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, Collections.singleton(GmailScopes.GMAIL_SEND)).build();
//
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().build();
//
//        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize(USER);
//
//        // Auth
//        gmail = new Gmail.Builder(httpTransport, JacksonFactory.getDefaultInstance(), credential).setApplicationName(applicationId).build();

        SpringApplication.run(CouponWebServer.class, args);
    }

    public static void sendEmail(Coupon coupon) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USER));
            message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(coupon.getReceipient()));
            message.setSubject(String.format("%d %s %s", coupon.getQuantity(),
                    coupon.isRedeemable() ? "Redeemable" : "Non-Redeemable", coupon.getAction())); // Eg. 1 Non-Redeemable High Five
            message.setText(String.format("Hello. Your coupon (%d %s) can be found here, %s", coupon.getQuantity(), coupon.getAction(), "http://raieen.xyz:8080/coupon/" + coupon.getId()));

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
