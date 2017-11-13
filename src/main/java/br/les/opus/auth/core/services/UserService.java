package br.les.opus.auth.core.services;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import br.les.opus.auth.core.domain.Resource;
import br.les.opus.auth.core.domain.Role;
import br.les.opus.auth.core.domain.User;
import br.les.opus.auth.core.domain.UserRole;
import br.les.opus.auth.core.repositories.ResourceRepository;
import br.les.opus.auth.core.repositories.RoleRepository;
import br.les.opus.auth.core.repositories.UserRepository;
import br.les.opus.auth.core.repositories.UserRoleRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleDao;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserRoleRepository userRoleDao;
	
	@Autowired
	private ResourceRepository resourceDao;
	
	private List<Role> getDefaultRoles() {
		String rolesList = env.getProperty("user.roles.default");
		StringTokenizer tokenizer = new StringTokenizer(rolesList);
		List<Role> roles = new ArrayList<>();
		while (tokenizer.hasMoreTokens()) {
			Role role = roleDao.findByAuthority(tokenizer.nextToken());
			roles.add(role);
		}
		return roles;
	}
	
	/**
	 * Adds the default roles to a specified user
	 * @param user
	 */
	public void loadDefaultRoles(User user) {
		List<Role> defaultRoles = this.getDefaultRoles();
		for (Role role : defaultRoles) {
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setUser(user);
			userRoleDao.save(userRole);
		}
	}
	
	public User loadRolesAndResorces(User user) {
		List<Role> roles = roleDao.findAllByUser(user);
		user.setRoles(roles);
		
		List<Resource> resources = resourceDao.findAllByUser(user);
		user.setResources(resources);
		return user;
	}
	
	/**
	 * Creates a new user along with its default roles
	 * @param user
	 * @return
	 */
	public User save(User user) {
		User newUser = userRepository.save(user);
		loadDefaultRoles(newUser);
		return newUser;
	}
}
