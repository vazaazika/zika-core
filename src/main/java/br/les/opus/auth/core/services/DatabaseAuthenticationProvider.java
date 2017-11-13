package br.les.opus.auth.core.services;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import br.les.opus.auth.core.domain.User;
import br.les.opus.auth.core.repositories.UserRepository;

@Component
public class DatabaseAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authRequest = (UsernamePasswordAuthenticationToken)authentication;
		String username = (String)authRequest.getPrincipal();
		String password = (String)authRequest.getCredentials();
		String passwordMd5 = DigestUtils.md5Hex(password);
		
		User user = userRepository.findByUsernameAndPassword(username, passwordMd5);
		if (user == null) {
			throw new BadCredentialsException("Credenciais de acesso inválidas");
		}
		
		if (!user.isAccountNonExpired()) {
			throw new BadCredentialsException("Conta expirada");
		}
		
		if (!user.isAccountNonLocked()) {
			throw new BadCredentialsException("Conta bloqueada");
		}
		
		if (!user.isCredentialsNonExpired()) {
			throw new BadCredentialsException("Credenciais expiradas");
		}
		
		return authRequest;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
