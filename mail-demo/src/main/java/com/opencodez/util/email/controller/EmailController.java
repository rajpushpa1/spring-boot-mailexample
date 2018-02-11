/**
 * 
 */
package com.opencodez.util.email.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opencodez.util.Constants;
import com.opencodez.util.email.Email;
import com.opencodez.util.email.EmailService;
import com.opencodez.util.email.EmailTemplate;

/**
 * @author
 *
 */
@RestController
@RequestMapping("/api")
public class EmailController {

	@Autowired
	private EmailService emailService;
	@Autowired
	private EmailTemplate emailTemplate;

	@GetMapping(value = "/mail")
	public void sendMail() throws MessagingException {
		String from = Constants.FROM;
		List<String> to = Arrays.asList("rajpushpa025@gmail.com");
		String subject = Constants.SUBJECT;

		emailTemplate.Template("hello-world.html");

		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", "Pavan");
		replacements.put("today", String.valueOf(new Date()));
		String message = emailTemplate.getTemplate(replacements);

		System.out.println("msg" + message);

		Email email = new Email();
		email.setTo(to);
		email.setFrom(from);
		email.setSubject(subject);
		email.setMessage(message);
		email.setHtml(true);
		emailService.send(email);
		System.out.println(email);

	}

}
