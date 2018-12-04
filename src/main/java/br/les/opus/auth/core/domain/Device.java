package br.les.opus.auth.core.domain;


import br.les.opus.commons.persistence.IdAware;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "device")
public class Device{

    @Id
    @JsonIgnore
    @GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
    @SequenceGenerator(name="generator", sequenceName="SQ_PK_DEVICE")
    private long id;

    @Column(unique = true, nullable = false)
    private String token;


    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

    public Device(String token, Date creationTime, User user) {
        this.token = token;
        this.creationTime = creationTime;
        this.user = user;
    }

    public Device(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}