package br.les.opus.gamification.domain;

import br.les.opus.auth.core.domain.User;
import br.les.opus.commons.persistence.IdAware;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import static java.util.UUID.randomUUID;

/**
 * 
 * Created by andersonjso on 5/15/18.
 */
@Entity
@Table(name = "game_invite")
@PrimaryKeyJoinColumn(name = "invite_id")
public class Invite implements IdAware<Long>{

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
    @SequenceGenerator(name="generator", sequenceName="SQ_PK_GAME_INVITE")
    private Long id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(unique = true, nullable = false)
    private String hashedToken;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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

    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
