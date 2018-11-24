package br.les.opus.commons.persistence.filtering.converters;

import br.les.opus.gamification.domain.challenge.ChallengeType;

public class ChallengeTypeConverter implements ValueConverter {

	@Override
	public Object convert(String value) {
		ChallengeType type = new ChallengeType();
		type.setId(Long.valueOf(value));
		return type;
	}

}