package br.les.opus.gamification.domain;

import br.les.opus.auth.core.domain.User;
import br.les.opus.dengue.core.domain.Picture;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

import static java.util.UUID.randomUUID;

/**
 * Created by andersonjso on 5/16/18.
 */
@Entity
@Table(name = "user_password_reset")
public class ResetPassword {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
    @SequenceGenerator(name="generator", sequenceName="SQ_PK_GAME_INVITE")
    private Long id;

    @Column(nullable = false)
    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @Column(unique = true, nullable = false)
    private String hashedToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public ResetPassword(){
        this.hashedToken = randomUUID().toString();
        setExpirationDate();
    }

    public void setExpirationDate(){
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, 1);

        this.expirationDate = cal.getTime();

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getHashedToken() {
        return hashedToken;
    }

    public void setHashedToken(String hashedToken) {
        this.hashedToken = hashedToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*
    Date date = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.MONTH, 6);

            java.util.Date expirationDate = cal.getTime();

    System.err.println(expirationDate);
     */

    /*
    Date date = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    cal.add(Calendar.MONTH, 6);

            java.util.Date expirationDate = cal.getTime();

    System.err.println(expirationDate);
     */
}



/*
@Entity
@Table(name = "game_invite")
@PrimaryKeyJoinColumn(name = "invite_id")
public class Invite {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
    @SequenceGenerator(name="generator", sequenceName="SQ_PK_GAME_INVITE")
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(unique = true, nullable = false)
    private String hashedToken;

    public Invite() {
        this.quantity = 0;
        this.hashedToken = randomUUID().toString();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getHashedToken() {
        return hashedToken;
    }

    public void setHashedToken(String hashedToken) {
        this.hashedToken = hashedToken;
    }
}
 */