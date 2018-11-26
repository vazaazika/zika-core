package br.les.opus.gamification.domain.feedback;


import br.les.opus.commons.persistence.IdAware;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "feedback")
@Inheritance(strategy = InheritanceType.JOINED)
public class Feedback implements IdAware<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "SQ_PK_FEEDBACK")
    private Long id;

    @NotNull
    @Length(max = 100)
    @Column(nullable = false, unique=false)
    private String title;

    @NotNull
    @Length(max = 250)
    @Column(nullable = false, unique=false)
    private String body;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}



