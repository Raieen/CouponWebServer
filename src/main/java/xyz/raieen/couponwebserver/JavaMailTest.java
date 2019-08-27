package xyz.raieen.couponwebserver;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMailTest {


    public static void main(String[] args) {
        String pass = "ENGwu49fKagXGzZjaFyv9HfNCArg9J7Ahdykq7Dw8p2hUr2Jw79YNJzB9FfxNQ9HXtiKw4tstSKMKW3QiVZfWPDAcT9nfzKkLGeh";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("highfivecouponnoreply@gmail.com", pass);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("highfivecouponnoreply@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("ryansue00@gmail.com")
            );
            message.setSubject("Testing Gmail TLS");
            message.setText("Deeee");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}