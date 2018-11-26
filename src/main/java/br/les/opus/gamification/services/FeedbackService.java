package br.les.opus.gamification.services;


import br.les.opus.gamification.domain.feedback.FeedbackPoiInformationQuality;
import br.les.opus.gamification.repositories.FeedbackChangePoiStatusRepository;
import br.les.opus.gamification.repositories.FeedbackPoiInformationQualityRepository;
import br.les.opus.gamification.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackChangePoiStatusRepository changePoiStatusRepository;

    @Autowired
    private FeedbackPoiInformationQualityRepository poiInformationQualityRepository;


    public FeedbackPoiInformationQuality saveFeedbackInformationQuality(FeedbackPoiInformationQuality informationQuality) {

        FeedbackPoiInformationQuality feedbackPoiInformationQuality = poiInformationQualityRepository.save(informationQuality);
        return feedbackPoiInformationQuality;


    }
}