package br.les.opus.gamification.services;

import br.les.opus.commons.mail.AppConfigMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
public class MailService extends Thread {

    @Autowired
    AppConfigMail mailSender;

    @Autowired
    private Environment env;


    private String to;
    private String subject;
    private String text;

    public void run() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.getMailSender().send(message);
    }

    public JavaMailSender getMailSender() {
        return mailSender.getMailSender();
    }

    public void setMailSender(AppConfigMail mailSender) {
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

        String inviteLink = env.getProperty("host.default") + "join";

        String message = personalizedMessage + '\n';

        message += "Seu amigo " + player.getName() + " está convidadndo você para usar o VazaZika.\n" +
                "Por favor, use o seguinte link para se registrar.\n";
        message += inviteLink;

        return message;
    }

    public void setMail(MailBody mail) {
        this.setTo(mail.getTo());
        this.setSubject(mail.getSubject());
        this.setText(mail.getText());
    }


}