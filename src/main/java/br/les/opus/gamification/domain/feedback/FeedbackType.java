package br.les.opus.gamification.domain.feedback;


import br.les.opus.commons.persistence.IdAware;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "feedback_type")
@Inheritance(strategy = InheritanceType.JOINED)
public class FeedbackType implements IdAware<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "SQ_PK_FEEDBACK_TYPE")
    private Long id;

    @NotNull
    @Length(max = 100)
    @Column(nullable = false, unique=false)
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}



