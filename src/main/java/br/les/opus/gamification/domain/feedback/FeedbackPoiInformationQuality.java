package br.les.opus.gamification.domain.feedback;


import br.les.opus.dengue.core.domain.PointOfInterest;
import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "feedback_poi_information_quality")
@PrimaryKeyJoinColumn(name = "feedback_id")
public class FeedbackPoiInformationQuality extends Feedback {



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


    @NotNull
    @Type(type="true_false")
    private boolean resolved;


    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
