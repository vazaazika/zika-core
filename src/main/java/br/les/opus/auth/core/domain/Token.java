package br.les.opus.auth.core.domain;

import java.util.Date;
import java.util.List;

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

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "token")
public class Token {

	@Id
	@JsonIgnore
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_TOKEN")
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String hashedToken;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastTimeUsed;
	
	private Boolean longLasting;
	
	public Token() {
		this.creationTime = new Date();
		this.lastTimeUsed = this.creationTime;
	}
	
	public boolean hasGrantedPermission(Resource targetResource) {
		User user = this.getUser();
		List<Resource> resources = user.getAllResources();
		for (Resource resource : resources) {
			if (resource.matches(targetResource)) {
				return true;
			}
		}
		return false;
	}
	
	public Resource matchedSystemResource(Resource targetResource) {
		User user = this.getUser();
		List<Resource> resources = user.getAllResources();
		
		/*
		 * Getting the best match in the resources
		 */
		int bestMatchValue = Integer.MAX_VALUE;
		int matchValue;
		Resource resourceMatch = null;
		
		for(Resource resource: resources) {
			if(resource.getId() == 14L) {
				System.out.println("entrou");
			}
			if(resource.matches(targetResource)) {
				matchValue = Math.abs(targetResource.getUri().compareTo(resource.getUri()));
				if(matchValue < bestMatchValue) {
					bestMatchValue = matchValue;
					resourceMatch = resource;
				}
			}
		}
		return resourceMatch;
	}
	
	
	public Token(UsernamePasswordAuthenticationToken authToken) {
		this();
		StringBuffer buffer = new StringBuffer();
		buffer.append(authToken.getPrincipal().toString());
		buffer.append(authToken.getCredentials().toString());
		buffer.append(new Date().toString());
		this.hashedToken = DigestUtils.md5Hex(buffer.toString());
	}
	
	public Token(User user) {
		this();
		StringBuffer buffer = new StringBuffer();
		buffer.append(user.getUsername());
		buffer.append(Math.random());
		buffer.append(new Date().toString());
		this.hashedToken = DigestUtils.md5Hex(buffer.toString());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	@Override
	public String toString() {
		return "Token [id=" + id + ", user=" + user + "]";
	}

	public Date getLastTimeUsed() {
		return lastTimeUsed;
	}

	public void setLastTimeUsed(Date lastTimeUsed) {
		this.lastTimeUsed = lastTimeUsed;
	}

	public Boolean getLongLasting() {
		return longLasting;
	}

	public void setLongLasting(Boolean longLasting) {
		this.longLasting = longLasting;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
