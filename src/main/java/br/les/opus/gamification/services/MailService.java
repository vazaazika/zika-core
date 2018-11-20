package br.les.opus.gamification.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.les.opus.gamification.domain.MailBody;
import br.les.opus.gamification.domain.Player;

/**
 * Created by andersonjso on 5/10/18.
 */
@Service
@Configuration
public class MailService extends Thread{

    @Autowired
    JavaMailSender mailSender;

    private String to;
    private String subject;
    private String text;

    public void run(){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String buildMessage(Player player, String personalizedMessage) {

        String message = personalizedMessage + '\n';

        message += "Your friend " + player.getName() + " is inviting you to use VazaZika.\n" +
                "Please use the following link to access and register on our page:\n" +
                "http://vazazika.inf.puc-rio.br/join";

        return message;
    }

	public void setMail(MailBody mail) {
		this.setTo(mail.getTo());
		this.setSubject(mail.getSubject());
		this.setText(mail.getText());
	}


}