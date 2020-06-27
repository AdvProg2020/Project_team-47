package model.others;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {
    private static final Properties mailProperties;
    private static String htmlPage;
    private static final String email = "online.shop4787@gmail.com";
    private static String password;

    static {
        mailProperties = new Properties();
        mailProperties.put("mail.smtp.host", "smtp.gmail.com");
        mailProperties.put("mail.smtp.port", "465");
        mailProperties.put("mail.smtp.auth", "true");
        mailProperties.put("mail.smtp.socketFactory.port", "465");
        mailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    }

    private final String recipients;
    private String subject;
    private String content;
    private String message;

    public Email(String recipients) {
        this.recipients = recipients;
    }

    public static void setPassword(String password) {
        Email.password = password;
    }

    public static void setHtmlPage(String htmlPage) {
        Email.htmlPage = htmlPage;
    }

    public static void checkPassword() {
        Email checkingEmailPassword = new Email(Email.email);
        checkingEmailPassword.subject = "Checking Password";
        checkingEmailPassword.content = "Successful";
        checkingEmailPassword.sendWithoutThread();
    }

    private void sendWithoutThread() {
        try {
            Message message = createMessage();
            if (message == null)
                return;
            Transport.send(message);
        } catch (AuthenticationFailedException e) {
            System.out.println("\nYou entered wrong password for email!!");
            System.exit(2);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private Message createMessage() {
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
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendVerificationEmail() {
        this.subject = "Verification Code";
        String emailHtml = htmlPage.replace("TITLE_PLACE", "Verify your email address");
        emailHtml = emailHtml.replace("TEXT_PLACE", "Thanks for signing up for our online shop! We're excited to have you as an early\n" +
                "user.\n" +
                "<br>\n" +
                "Please enter below code in shop app!!");
        emailHtml = emailHtml.replace("CODE_PLACE", this.message);
        this.content = emailHtml;
        send();
    }

    public void sendForgotPasswordEmail() {
        this.subject = "Forgot Password";
        String emailHtml = htmlPage.replace("TITLE_PLACE", "Forgot password");
        emailHtml = emailHtml.replace("TEXT_PLACE", "Please enter below code in shop app to " +
                "change your account password.");
        emailHtml = emailHtml.replace("CODE_PLACE", this.message);
        this.content = emailHtml;
        send();
    }

    public void sendBuyingEmail() {
        this.subject = "Buying";
        String emailHtml = htmlPage.replace("TITLE_PLACE", "Successful buy");
        emailHtml = emailHtml.replace("TEXT_PLACE", "You purchased successfully and your buying log's id is:");
        emailHtml = emailHtml.replace("CODE_PLACE", this.message);
        this.content = emailHtml;
        send();
    }

    public void sendDiscountEmail(int percent) {
        this.subject = "Discount code";
        String emailHtml = htmlPage.replace("TITLE_PLACE", "Discount Code");
        emailHtml = emailHtml.replace("TEXT_PLACE", "You get a discount with " + percent +
                " percent for buying from our shop.<br> You have 1 week to use this code. Here is the code:");
        emailHtml = emailHtml.replace("CODE_PLACE", this.message);
        this.content = emailHtml;
        send();
    }

    private void send() {
        (new SendingThread(createMessage())).start();
    }

    private Session createSession() {
        return Session.getInstance(mailProperties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private static class SendingThread extends Thread {
        private final Message message;

        SendingThread(Message message) {
            this.message = message;
        }

        public void run() {
            try {
                Transport.send(message);
            } catch (AuthenticationFailedException e) {
                System.out.println("\nYou entered wrong password for email!!");
                System.exit(2);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }


}
