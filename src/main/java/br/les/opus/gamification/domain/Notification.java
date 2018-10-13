package br.les.opus.gamification.domain;

import br.les.opus.auth.core.domain.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "game_notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "SQ_PK_NOTIFICATION")
    private Long Id;

    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private boolean isRead;

    @ManyToOne
    private User user;


    public Notification(String message, Date createdDate,User user){
        this.message = message;
        this.createdDate = createdDate;
        this.user = user;
        this.isRead = false;
    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}