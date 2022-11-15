package com.myapp.springawsses.controller;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.myapp.springawsses.config.AwsPropertyConfig;
import com.myapp.springawsses.entity.Mail;
import com.myapp.springawsses.service.MailService;

@Controller
public class MailController {

	private MailService mailService;
	private AwsPropertyConfig awsPropertyConfig;

	public MailController(MailService emailSenderService, AwsPropertyConfig customPropertyConfig) {
		this.mailService = emailSenderService;
		this.awsPropertyConfig = customPropertyConfig;
	}
	
	@GetMapping("/")
	public String indexPage() {
		return "index";
	}

	@PostMapping(value = "/send")
	public String sendMail(@ModelAttribute("mail") Mail mail1) throws MessagingException {
		Mail mail = getMail(mail1.getTo(), mail1.getSubject());
		mailService.sendEmail(mail);
		return "success";
	}

	private Mail getMail(String to, String subject) {
		Mail mail = new Mail();
		mail.setFrom(awsPropertyConfig.mailFrom);
		mail.setTo(to);
		mail.setSubject(subject);
		Map<String, Object> model = new HashMap<>();
		model.put("templateVariable", "Simple mail with aws..");
		mail.setModel(model);
		return mail;

	}
}
