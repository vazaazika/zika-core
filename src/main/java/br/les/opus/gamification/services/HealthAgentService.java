package br.les.opus.gamification.services;


import br.les.opus.auth.core.domain.Resource;
import br.les.opus.auth.core.domain.Role;
import br.les.opus.auth.core.domain.User;
import br.les.opus.auth.core.domain.UserRole;
import br.les.opus.auth.core.repositories.ResourceRepository;
import br.les.opus.auth.core.repositories.RoleRepository;
import br.les.opus.auth.core.repositories.UserRepository;
import br.les.opus.auth.core.repositories.UserRoleRepository;
import br.les.opus.gamification.domain.HealthAgent;
import br.les.opus.gamification.repositories.HealthAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class HealthAgentService {

    @Autowired
    private HealthAgentRepository healthAgentRepository;

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

    /**
     * Creates a new user along with its default roles
     * @param user
     * @return
     */
    public HealthAgent save(HealthAgent user) {
        HealthAgent newUser = userRepository.save(user);
        loadDefaultRoles(newUser);
        loadRolesAndResorces(newUser);
        //generateInviteID(newUser);
        return newUser;
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

    private List<Role> getDefaultRoles() {
        String rolesList = env.getProperty("user.roles.healthagent");
        StringTokenizer tokenizer = new StringTokenizer(rolesList);
        List<Role> roles = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            Role role = roleDao.findByAuthority(tokenizer.nextToken());
            roles.add(role);
        }
        return roles;
    }

    public HealthAgent loadRolesAndResorces(HealthAgent user) {
        List<Role> roles = roleDao.findAllByUser(user);
        user.setRoles(roles);

        List<Resource> resources = resourceDao.findAllByUser(user);
        user.setResources(resources);
        return user;
    }


    public HealthAgent findById(User user) {
        HealthAgent  healthAgent = healthAgentRepository.findById(user.getId());
        loadRolesAndResorces(healthAgent);
        return healthAgent;
    }

    public HealthAgent findById(Long id) {
        HealthAgent  healthAgent = healthAgentRepository.findById(id);
        loadRolesAndResorces(healthAgent);
        return healthAgent;
    }

}


