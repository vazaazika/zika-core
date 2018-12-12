package br.les.opus.test.util;


import br.les.opus.gamification.domain.feedback.Feedback;

import java.util.GregorianCalendar;

public class CreateFeedback {



    /**
     * Return a Point of Interest
     */
    public static Feedback create() {

        Feedback feedback = new Feedback();
        feedback.setTitle("Feedback titulo");
        feedback.setBody("Corpo da mensagem");
        feedback.setDate(new GregorianCalendar().getTime());

        return feedback;
    }




}
