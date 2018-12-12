package br.les.opus.gamification.services;


import br.les.opus.gamification.domain.feedback.FeedbackPoiInformationQuality;
import br.les.opus.gamification.domain.feedback.FeedbackType;
import br.les.opus.gamification.repositories.FeedbackPoiInformationQualityRepository;
import br.les.opus.gamification.repositories.FeedbackRepository;
import br.les.opus.gamification.repositories.FeedbackTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackTypeRepository feedbackTypeRepository;

    @Autowired
    private FeedbackPoiInformationQualityRepository poiInformationQualityRepository;


    public FeedbackPoiInformationQuality saveFeedbackInformationQuality(FeedbackPoiInformationQuality informationQuality) {

        FeedbackPoiInformationQuality feedbackPoiInformationQuality = poiInformationQualityRepository.save(informationQuality);
        return feedbackPoiInformationQuality;


    }

    public FeedbackType saveFeedbackType(FeedbackType feedbackType) {
        FeedbackType feedbackTypeR = feedbackTypeRepository.save(feedbackType);
        return feedbackTypeR;
    }
}