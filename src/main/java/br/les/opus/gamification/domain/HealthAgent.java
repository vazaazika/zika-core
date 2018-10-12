package br.les.opus.gamification.domain;

import br.les.opus.auth.core.domain.User;
import br.les.opus.gamification.LevelingSystem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "game_player_health_agent")
@PrimaryKeyJoinColumn(name = "user_id")
public class HealthAgent extends User {

    @Length(max = 100)
    @Column(nullable = false, unique=true)
    private String nickname;

    @Column(nullable = false)
    private Long xp;

    @Column(nullable = false)
    private Integer level;

    @JsonIgnore
    @OneToMany(mappedBy = "player_health_agent")
    private List<PerformedTask> performedTasks;


    @JsonIgnore
    @OneToMany(mappedBy = "player_health_agent")
    private List<TaskGroupProgression> progressions;



    public HealthAgent(User user) {
        this();
        this.setName(user.getName());
        this.setUsername(user.getUsername());
        this.setPassword(user.getPassword());
        this.setNickname(user.getUsername());
    }

    public HealthAgent() {
        super();
        this.xp = 0l;
        this.level = 1;
    }

    public HealthAgent(Integer level, String nickname, Long xp) {
        super();
        this.level = level;
        this.nickname = nickname;
        this.xp = xp;
    }

    @Transient
    public Integer getXpCurrentLevel() {
        return LevelingSystem.requiredXp(this.level);
    }

    @Transient
    public Integer getXpNextLevel() {
        return LevelingSystem.requiredXp(this.level + 1);
    }

    public void addXp(Integer xp) {
        this.xp += xp;
        this.level = LevelingSystem.computeLevel(this.xp.intValue());
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getXp() {
        return xp;
    }

    public void setXp(Long xp) {
        this.xp = xp;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<PerformedTask> getPerformedTasks() {
        return performedTasks;
    }

    public void setPerformedTasks(List<PerformedTask> performedTasks) {
        this.performedTasks = performedTasks;
    }

    public List<TaskGroupProgression> getProgressions() {
        return progressions;
    }

    public void setProgressions(List<TaskGroupProgression> progressions) {
        this.progressions = progressions;
    }

    @Override
    public String toString() {
        return "Player_Health_Agent [nickname=" + nickname + "]";
    }


}



