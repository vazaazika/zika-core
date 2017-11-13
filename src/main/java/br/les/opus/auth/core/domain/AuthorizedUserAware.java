package br.les.opus.auth.core.domain;

/**
 * Any class that implements this interface will have
 * the current logged user set when a new object is persisted 
 * @author Diego Cedrim
 */
public interface AuthorizedUserAware {
	
	User getUser();
	
	void setUser(User user);
}
