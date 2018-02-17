/**
 * 
 */
package com.mail.util.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.mail.util.ApplicationLogger;


@Component
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	public EmailService(JavaMailSender javaMailSender) {

		this.mailSender = javaMailSender;
	}

	private static final ApplicationLogger logger = ApplicationLogger
			.getInstance();

	public void send(Email eParams) {

		if (eParams.isHtml()) {
			try {
				sendHtmlMail(eParams);
			} catch (MessagingException e) {
				logger.error("Could not send email to : {} Error = {}",
						eParams.getToAsList(), e.getMessage());
			}
		}
	}

	private void sendHtmlMail(Email email) throws MessagingException {

		boolean isHtml = true;

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(email.getTo().toArray(new String[email.getTo().size()]));
		helper.setReplyTo(email.getFrom());
		helper.setFrom(email.getFrom());
		helper.setSubject(email.getSubject());
		helper.setText(email.getMessage(), isHtml);

		/*
		 * if (email.getCc().size() > 0) {
		 * helper.setCc(email.getCc().toArray(new
		 * String[eParams.getCc().size()])); }
		 */

		mailSender.send(message);

		System.out.println("mail send");
	}

}
