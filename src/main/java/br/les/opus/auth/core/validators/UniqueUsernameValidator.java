package br.les.opus.auth.core.validators;

import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.les.opus.auth.core.domain.User;
import br.les.opus.auth.core.repositories.UserRepository;

@Component
@Transactional
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, User> {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void initialize(UniqueUsername constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(User value, ConstraintValidatorContext context) {
		User userFound = userRepository.findByUsername(value.getUsername());
		if (userFound == null) {
			return true;
		}
		return userFound.getId().equals(value.getId());
	}


}
