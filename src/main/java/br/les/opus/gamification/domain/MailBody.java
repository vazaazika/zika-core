package br.les.opus.gamification.domain;

/**
 * Created by andersonjso on 5/15/18.
 */
public class MailBody {

    private String to;
    private String subject;
    private String text;


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
}
