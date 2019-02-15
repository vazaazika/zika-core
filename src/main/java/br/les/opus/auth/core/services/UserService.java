package br.les.opus.auth.core.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import br.les.opus.auth.core.domain.Resource;
import br.les.opus.auth.core.domain.Role;
import br.les.opus.auth.core.domain.User;
import br.les.opus.auth.core.domain.UserRole;
import br.les.opus.auth.core.repositories.ResourceRepository;
import br.les.opus.auth.core.repositories.RoleRepository;
import br.les.opus.auth.core.repositories.UserRepository;
import br.les.opus.auth.core.repositories.UserRoleRepository;
import br.les.opus.gamification.domain.Invite;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.ResetPassword;
import br.les.opus.gamification.repositories.InviteRepository;
import br.les.opus.gamification.repositories.ResetPasswordRepository;
import br.les.opus.gamification.services.MailService;

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

    @Autowired
    private InviteRepository inviteDao;

    @Autowired
    private ResetPasswordRepository resetPasswordDao;

    @Autowired
    private MailService mailService;

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
     *
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

    public void generateInviteID(User user) {
        Invite invite = user.getInvite();

        inviteDao.save(invite);
    }

    /**
     * Creates a new user along with its default roles
     *
     * @param user
     * @return
     */
    public User save(User user) {
        Player player = new Player(user);
        User newUser = userRepository.save(player);
        loadDefaultRoles(newUser);
        generateInviteID(newUser);
        return newUser;
    }

    /**
     * Retrieve a user using its identification token
     *
     * @param token
     * @return
     */
//	public User loadUserByToken (String token){
//		return userRepository.findUserByInvitationToken(token);
//
//	}
    public void changePassword(User user) {
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setEmail(user.getUsername());

        String message = "Por favor, acesse o seguinte link para alterar sua senha:\n";
        message += env.getProperty("host.default") + "password/renew?token-reset=";
        message += resetPassword.getHashedToken();

        mailService.setSubject("Password Reset");
        mailService.setTo(user.getUsername());
        mailService.setText(message);

        mailService.run();

        resetPasswordDao.save(resetPassword);
    }

    public User resetPassword(String tokenReset, String newPassword) {

        ResetPassword resetPassword = resetPasswordDao.findToken(tokenReset);

        if (resetPassword == null) {
            throw new BadCredentialsException("Token não encontrado ou já usado");
        }
        Calendar cal = Calendar.getInstance();
        if ((resetPassword.getExpirationDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            throw new BadCredentialsException("O token informado está expirado");
        }

        User user = userRepository.findByUsername(resetPassword.getEmail());

        user.setPassword(DigestUtils.md5Hex(newPassword));

        resetPasswordDao.delete(resetPassword.getId());

        return user;

    }
}
