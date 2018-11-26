package br.les.opus.test.gamification.repositories;

import br.les.opus.gamification.domain.feedback.Feedback;
import br.les.opus.gamification.domain.feedback.FeedbackChangePoiStatus;
import br.les.opus.gamification.repositories.FeedbackChangePoiStatusRepository;
import br.les.opus.gamification.repositories.FeedbackPoiInformationQualityRepository;
import br.les.opus.gamification.repositories.FeedbackRepository;
import br.les.opus.test.util.CreateFeedback;
import br.les.opus.test.util.DbTestUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeedbackRepositoryTest extends DbTestUtil {


    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackChangePoiStatusRepository feedbackChangePoiStatusRepository;

    @Autowired
    private FeedbackPoiInformationQualityRepository feedbackPoiInformationQualityRepository;



    private Feedback feedbacks[] = new Feedback[4];

    private FeedbackChangePoiStatus changePoiStatus[] = new FeedbackChangePoiStatus[2];



    @Before
    public void initializer() {

        feedbacks[0] = CreateFeedback.create();
        feedbacks[1] = CreateFeedback.create();
        feedbacks[2] = CreateFeedback.create();
        feedbacks[3] = CreateFeedback.create();


        feedbacks[0] = feedbackRepository.save(feedbacks[0]);
        feedbacks[1] = feedbackRepository.save(feedbacks[1]);
        feedbacks[2] = feedbackRepository.save(feedbacks[2]);
        feedbacks[3] = feedbackRepository.save(feedbacks[3]);

    }


    @Test
    public void existingFeedback() {

        List<Feedback> feedbackArrayList = new ArrayList<>();
        feedbackArrayList.add(feedbacks[0]);
        feedbackArrayList.add(feedbacks[1]);
        feedbackArrayList.add(feedbacks[2]);
        feedbackArrayList.add(feedbacks[3]);


        Assert.assertEquals(feedbackArrayList, feedbackRepository.findAll());
    }




    @After
    public void restoreDB() {

        feedbackRepository.delete(Arrays.asList(feedbacks));

    }



}
