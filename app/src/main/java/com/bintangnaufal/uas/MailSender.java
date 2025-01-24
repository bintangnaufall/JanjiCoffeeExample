package com.bintangnaufal.uas;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    private final String emailPengirim = "janjijiwaopicial@gmail.com"; // Email pengirim
    private final String password = "gwzo lyix bvir oaqu"; // Password aplikasi Gmail

    public void sendMail(String recipientEmail, String subject, String messageBody) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailPengirim, password);
            }
        });

        Message message = prepareMessage(session, recipientEmail, subject, messageBody);
        Transport.send(message);
    }

    private Message prepareMessage(Session session, String recipientEmail, String subject, String messageBody)
            throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailPengirim));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }
}
