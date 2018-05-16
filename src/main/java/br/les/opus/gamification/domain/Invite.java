package br.les.opus.gamification.domain;

import javax.persistence.*;

import static java.util.UUID.randomUUID;

/**
 * Created by andersonjso on 5/15/18.
 */
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
