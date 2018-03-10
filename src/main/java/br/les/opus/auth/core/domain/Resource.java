package br.les.opus.auth.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "resource")
public class Resource {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="generator")
	@SequenceGenerator(name="generator", sequenceName="SQ_PK_RESOURCE")
	private Long id;
	
	/**
	 * URI (ou uma expressão regular) indicando o recurso a ser acessado
	 */
	@NotEmpty
	@Column(nullable = false)
	private String uri;
	
	
	/**
	 * Nome da operação (ou uma expressão regular) indicando o tipo de atividade
	 * que pode ser executada no recurso
	 */
	@NotEmpty
	@Column(nullable = false)
	private String operation;
	
	/**
	 * Indicates whether a resource is public or not. 
	 * Authentication  interceptors always allow requests to
	 * open resources  
	 */
	private Boolean open;
	
	public Resource() {
		this.open = false;
	}
	
	/**
	 * Builds a resource from a given HTTP request
	 * @param request request used to build the resource object
	 */
	public Resource(HttpServletRequest request) {
		this();
		String uri = request.getRequestURI().substring(request.getContextPath().length());
		String operation = request.getMethod();
		
		this.setUri(uri);
		this.setOperation(operation);
	}
	
	public boolean matches(Resource resource) {
		String uriRegex = resource.getUri();
		String operationRegex = resource.getOperation();
		return uriRegex.matches(uri) && operationRegex.matches(operation); 
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

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Override
	public String toString() {
		return "Resource [id=" + id + ", uri=" + uri 
				+ ", operation=" + operation + "]";
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
		Resource other = (Resource) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}
	
}
