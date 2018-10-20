package br.les.opus.gamification.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.Point;

import br.les.opus.commons.persistence.IdAware;
import br.les.opus.dengue.core.domain.PerformedTaskComment;
import br.les.opus.dengue.core.domain.PointOfInterest;

/**
 * This class represents a registration of game task performed by a given user. It is a kind of
 * activities log that can be used for user ranking and progress monitoring.
 * 
 * @author Diego Cedrim
 */
@Entity
@Table(name = "game_performed_task")
public class PerformedTask implements IdAware<Long> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_GAME_PERFORMED_TASK")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "task_id")
	private Task task;
	
	@ManyToOne
	@JoinColumn(name = "player_id")
	private Player player;
	
	/**
	 * The location where the player was when performed the task (can be null).
	 */
	@Column(columnDefinition="Geometry")
	@Type(type="org.hibernate.spatial.GeometryType")
	private Point location;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Any(metaColumn = @Column(name="object_type"), fetch=FetchType.EAGER)
    @AnyMetaDef(
    			metaType = "string",
            idType = "long",
            metaValues = {
                    @MetaValue( value="poi", targetEntity=PointOfInterest.class ),
            }
    )
	@JoinColumn(name = "object_id")
	private Object object;
	
	@JsonIgnore
	@OneToMany(mappedBy = "performedTask")
	private List<PerformedTaskComment> comments;
	
	public PerformedTask() {
		this.date = new Date();
	}
	
	public PerformedTask(Task task, Player player) {
		this();
		this.task = task;
		this.player = player;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public List<PerformedTaskComment> getComments() {
		return comments;
	}

	public void setComments(List<PerformedTaskComment> comments) {
		this.comments = comments;
	}
	
}
