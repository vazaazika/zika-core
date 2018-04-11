package br.les.opus.gamification.validators;

import javax.transaction.Transactional;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.les.opus.gamification.domain.Team;
import br.les.opus.gamification.repositories.TeamRepository;

@Component
@Transactional
public class UniqueTeamNameValidator implements ConstraintValidator<UniqueTeamName, Team> {
	
	@Autowired
	private TeamRepository teamRepository;

	@Override
	public void initialize(UniqueTeamName constraintAnnotation) {
		
	}

	@Override
	public boolean isValid(Team value, ConstraintValidatorContext context) {
		Team teamFound = teamRepository.findByName(value.getName());
		if (teamFound == null) {
			return true;
		}
		return teamFound.getId().equals(value.getId());
	}


}
