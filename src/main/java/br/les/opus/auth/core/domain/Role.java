package br.les.opus.auth.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

import br.les.opus.commons.persistence.IdAware;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority, IdAware<Long> {
	
	private static final long serialVersionUID = -250949549378267771L;
	
	public static final String ROOT = "ROOT";
	public static final String HEALTH_AGENT = "HEALTH_AGENT";
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_ROLE")
	private Long id;
	
	@Column(unique = true)
	private String authority;
	
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	private List<RoleResource> roleResources;
	
	@ManyToOne
	@JsonIgnore
	private Role parent;

	/**
	 * Retorna a lista de todos os recursos associados ao papel
	 * e aos pais dele
	 * @return
	 */
	@JsonIgnore
	@Transient
	public List<Resource> getAllResources() {
		List<Resource> resources = new ArrayList<Resource>();
		Role role = this;
		while (role != null) {
			List<RoleResource> roleResources = role.getRoleResources();
			for (RoleResource roleResource : roleResources) {
				resources.add(roleResource.getResource());
			}
			role = role.getParent();
		}
		return resources;
	}
	
	@Override
	public String getAuthority() {
		return authority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Role getParent() {
		return parent;
	}

	public void setParent(Role parent) {
		this.parent = parent;
	}

	public List<RoleResource> getRoleResources() {
		return roleResources;
	}

	public void setRoleResources(List<RoleResource> roleResources) {
		this.roleResources = roleResources;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", authority=" + authority + "]";
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
		Role other = (Role) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
