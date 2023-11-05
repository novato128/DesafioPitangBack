package com.rentcars.pitang.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.rentcars.pitang.model.user.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SendMailService {

	@Value("${spring.mail.username}")
	private String from;
	
	@Autowired 
	private JavaMailSender mailSender;
	
	@Autowired
	private UserService userService;
	
//	@Scheduled(cron = "0 * * * * *", zone = "America/Sao_Paulo")
	public void sendMailSchedule() {
		List<User> users = this.userService.getAllUsers();
		users.forEach(user -> {
			if(user.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(30))){
				log.info(sendMail(user.getEmail()));
			}
		});
	}
	
	public String sendMail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText("Hello from Spring Boot Application");
        message.setTo(to);
        message.setFrom(from);

        try {
            mailSender.send(message);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar email.";
        }
    }
}
