package es.altair.bean;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {
	final String senderEmailId = "proyectofinaljfr@gmail.com";
	final String senderPassword = "altair123$%";
	final String emailSMTPserver = "smtp.gmail.com";

	public Mail(String receiverEmail, String subject, String messageText) {

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", emailSMTPserver);
	    props.put("mail.smtp.port", "465");  
	    props.put("mail.debug", "true");  
	    props.put("mail.smtp.socketFactory.port", "465");  
	    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
	    props.put("mail.smtp.socketFactory.fallback", "false"); 

		try {
			Authenticator auth = new SMTPAuthenticator();
			Session session = Session.getInstance(props, auth);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderEmailId));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiverEmail));
			message.setSubject(subject);
			message.setText(messageText);

			Transport.send(message);
			System.out.println("Email send successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error in sending email.");
		}
	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(senderEmailId, senderPassword);
		}
	}
}