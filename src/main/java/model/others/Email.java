package model.others;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {
    private static String email;
    private static String password;
    private static final Properties mailProperties;
    private static final String firstPartOfVerificationHtml;
    private static final String secondPartOfVerificationHtml;
    private static final String firstPartOfForgotPasswordHtml;
    private static final String secondPartOfForgoPasswordHtml;

    static {
        mailProperties = new Properties();
        mailProperties.put("mail.smtp.host", "smtp.gmail.com");
        mailProperties.put("mail.smtp.port", "465");
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.socketFactory.port", "465");
        mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        firstPartOfVerificationHtml = "";
        secondPartOfVerificationHtml = "";
        firstPartOfForgotPasswordHtml = "";
        secondPartOfForgoPasswordHtml = "";
    }

    private String recipients;
    private String subject;
    private String content;
    private String contentString;

    public Email(String recipients) {
        this.recipients = recipients;
    }

    public void sendVerificationEmail() {
        this.subject = "Verification Code";
        this.content = firstPartOfVerificationHtml + this.contentString + secondPartOfVerificationHtml;
        send();
    }


    public void sendForgotPasswordEmail() {
        this.subject = "Forgot Password";
        this.content = firstPartOfVerificationHtml + this.contentString + secondPartOfVerificationHtml;
        send();
    }

    public void sendBuyingEmail() {

    }

    private void send(){
        Session session = createSession();
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("rampshop@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(this.recipients)
            );

            message.setSubject(this.subject);
            message.setContent(this.content, "text/html");

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Session createSession() {
        return Session.getInstance(mailProperties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
    }

    public void setContentString(String string) {
        this.contentString = string;
    }

    public static void setShopEmail(String email, String password) {
        Email.email = email;
        Email.password = password;
    }
}
