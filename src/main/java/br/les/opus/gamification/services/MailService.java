package br.les.opus.gamification.services;

import br.les.opus.dengue.core.factories.MailRepositoryFactory;
import br.les.opus.gamification.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by andersonjso on 5/10/18.
 */
@Service
public class MailService{

    @Autowired
    JavaMailSender mailSender;

    public void send(String to, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public String buildMessage(Player player, String personalizedMessage) {

        String message = personalizedMessage + '\n';

        message += "Your friend " + player.getName() + " is inviting you to use VazaZika.\n" +
                "Please use the following link to access and register on our page:\n" +
                "http://vazazika.inf.puc-rio.br/join";

        return message;
    }
}