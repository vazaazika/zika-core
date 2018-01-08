package br.les.opus.gamification;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;

import br.les.opus.auth.core.domain.User;

/**
 * An {@link UserAction} represents a request performed by a user. This class
 * helps us to store all requests performed by the users. To represent the request,
 * we store the requested URI, HTTP method, and, also, the status code returned by the API.
 * In this way, we can track all successful and unsuccessful requests performed by all users.
 */
@Entity
@Table(name = "system_user_action")
public class UserAction {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_USER_ACTION")
	private Long id;

	/**
	 * The requested URI. By using this, we can track the specific resource
	 * requested by the user. We can say if he is uploading a picture, inserting
	 * a POI, or even liking a post.
	 */
	@NotEmpty
	@Column(nullable = false)
	private String uri;
	
	/**
	 * The HTTP method requested. This field, in combination with the URI, uniquely identifies
	 * an action performed by a user
	 */
	@NotEmpty
	@Column(nullable = false)
	private String method;
	
	/**
	 * The HTTP status code returned as result of the request. By using this, we can check if
	 * the request was successful or not.
	 */
	@NotEmpty
	@Column(nullable = false)
	private Integer status; 
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	public UserAction() {
		this.date = new Date();
	}

	public UserAction(String uri, String method, Integer status, User user) {
		this();
		this.uri = uri;
		this.method = method;
		this.status = status;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
