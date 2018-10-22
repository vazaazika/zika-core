package br.les.opus.gamification.constraints.checkers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Component;

import br.les.opus.gamification.constraints.AssignmentConstraint;
import br.les.opus.gamification.constraints.DurationConstraint;

@Component
public class DurationConstraintChecker{

	public Integer completedWork(AssignmentConstraint constraint, Date date) {
		DurationConstraint durationConstraint = (DurationConstraint) constraint;
		
		Duration duration = Duration.ofDays(durationConstraint.getDuration());
		
		LocalDateTime current = convertToLocalDateTimeViaInstant(new Date());
		LocalDateTime beginChallenge = convertToLocalDateTimeViaInstant(date);
		
		Duration difference = Duration.between(beginChallenge, current);
		
		if(duration.compareTo(difference) > 0) {
			return 0;
		}
		
		return 1;
	}
	
	private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
}
