package br.les.opus.gamification.domain.feedback;


import br.les.opus.dengue.core.domain.PointOfInterest;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

import javax.persistence.*;

@Entity
@Table(name = "feedback_poi_change_status")
@PrimaryKeyJoinColumn(name = "feedback_id")
public class FeedbackChangePoiStatus extends Feedback {


    @Any(metaColumn = @Column(name="object_type"), fetch= FetchType.EAGER)
    @AnyMetaDef(
            metaType = "string",
            idType = "long",
            metaValues = {
                    @MetaValue( value="poi", targetEntity= PointOfInterest.class ),
            }
    )
    @JoinColumn(name = "object_id")
    private Object object;


}
