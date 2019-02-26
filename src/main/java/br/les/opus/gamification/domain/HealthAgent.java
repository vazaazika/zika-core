package br.les.opus.gamification.domain;

import br.les.opus.auth.core.domain.User;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "health_agent")
@PrimaryKeyJoinColumn(name = "user_id")
public class HealthAgent extends Player {

    @NotNull
    @Length(max = 100)
    @Column(nullable = false, unique=false)
    private String organization;

    @NotNull
    @Length(max = 100)
    @Column(nullable = false, unique=false)
    private String state;

    @NotNull
    @Length(max = 100)
    @Column(nullable = false, unique=false)
    private String city;


    public HealthAgent(User user, String city, String state, String organization) {
        super(user);
        this.setCity(city);
        this.setState(state);
        this.setOrganization(organization);
    }

    public HealthAgent() {
        super();
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
