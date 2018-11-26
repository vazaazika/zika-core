package br.les.opus.auth.core.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import br.les.opus.gamification.domain.Invite;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.les.opus.auth.core.validators.UniqueUsername;
import br.les.opus.commons.persistence.IdAware;
import br.les.opus.dengue.core.domain.Picture;
import br.les.opus.dengue.core.domain.PoiStatusUpdate;
import br.les.opus.dengue.core.domain.PointOfInterest;

/**
 * Classe que representa um usuário. Essa classe implementa a
 * interface {@link UserDetails} do Spring Security para servir como entidade
 * autenticável no framework mencionado.
 * 
 * 
 * @author Diego Cedrim
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@UniqueUsername(payload = {}) //ensures validation on insert/update regarding user name
@Table(name = "system_user")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorOptions(force=true)
public class User implements UserDetails, IdAware<Long> {

	private static final long serialVersionUID = 5060765600109301997L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_USER")
	private Long id;

	@NotNull
	@Length(max = 250)
	@Column(nullable = false)
	private String name;

	@NotNull
	@Length(max = 250)
	@Column(nullable = false)
	private String password;

	@Email
	@NotNull
	@NaturalId
	@Length(max = 250)
	@Column(unique = true, nullable = false)
	private String username;

	private Boolean enabled;

	private Boolean locked;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<PointOfInterest> reports;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<PoiStatusUpdate> poiUpdates;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<UserRole> userRoles;
	
	@Transient
	private List<Role> roles;
	
	@Transient
	private List<Resource> resources;
	
	@JsonIgnore
	@Version
    @Column(name="opt_lock")
	private Integer version;
	
	@OneToOne
	@JoinColumn(name = "picture_id")
	private Picture avatar;


    @OneToOne (cascade = {CascadeType.ALL})
    @JoinColumn(name = "invite_id")
    private Invite invite;

	public User() {
		this.reports = new ArrayList<>();
		this.userRoles = new ArrayList<UserRole>();
		this.enabled = true;
		this.locked = false;
		this.version = 0;
        this.invite = new Invite();

    }
	
	@Override
	@Transient
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<Role> authorities = new ArrayList<Role>();
		for (UserRole userRole : userRoles) {
			authorities.add(userRole.getRole());
		}
		return authorities;
	}
	
	@JsonIgnore
	public boolean isRoot() {
		for (GrantedAuthority authority : this.getAuthorities()) {
			if (authority.getAuthority().equals(Role.ROOT)) {
				return true;
			}
		}
		return false;
	}

	@JsonIgnore
	public boolean isHealthAgent() {
		for (GrantedAuthority authority : this.getAuthorities()) {
			if (authority.getAuthority().equals(Role.HEALTH_AGENT)) {
				return true;
			}
		}
		return false;
	}



	@Transient
	@JsonIgnore
	public List<Resource> getAllResources() {
		List<Resource> resources = new ArrayList<Resource>();
		for (UserRole userRole : userRoles) {
			List<Resource> roleResource = userRole.getRole().getAllResources();
			for (Resource resource : roleResource) {
				resources.add(resource);
			}
		}
		return resources;
	}
	
	@Transient
	@JsonIgnore
	public UserRole findUserRole(Role role) {
		for (UserRole userRole : this.userRoles) {
			Role currentRole = userRole.getRole();
			if (currentRole != null && role.getId().equals(currentRole.getId()) ) {
				return userRole;
			}
		}
		return null;
	}
	
	@Transient
	public String getShortName() {
		if (StringUtils.isEmpty(this.name)) {
			return null;
		}
		StringTokenizer tokenizer = new StringTokenizer(this.name);
		StringBuffer shortName = new StringBuffer();
		shortName.append(tokenizer.nextToken());
		if (tokenizer.hasMoreTokens()) {
			shortName.append(" ");
			shortName.append(tokenizer.nextToken());
		}
		return shortName.toString();
	}
	
	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		if (locked == null) {
			return true;
		}
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username="
				+ username + "]";
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getVersion() {
		return version;
	}

	public List<PointOfInterest> getReports() {
		return reports;
	}

	public void setReports(List<PointOfInterest> reports) {
		this.reports = reports;
	}

	public List<PoiStatusUpdate> getPoiUpdates() {
		return poiUpdates;
	}

	public void setPoiUpdates(List<PoiStatusUpdate> poiUpdates) {
		this.poiUpdates = poiUpdates;
	}

	public Picture getAvatar() {
		return avatar;
	}

	public void setAvatar(Picture avatar) {
		this.avatar = avatar;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}


    public Invite getInvite() {
        return invite;
    }
}
