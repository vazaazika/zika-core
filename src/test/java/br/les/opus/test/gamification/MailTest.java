package br.les.opus.test.gamification;

import br.les.opus.gamification.services.MailService;
import org.junit.Test;

/**
 * Created by andersonjso on 5/10/18.
 */
public class MailTest {

    @Test
    public void mandaEmailPo(){
        MailService mailService = new MailService();

        mailService.send("anderson.jose.so@gmail.com", "eae man", "na moralz");
    }
}
