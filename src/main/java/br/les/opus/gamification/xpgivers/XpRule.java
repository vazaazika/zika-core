package br.les.opus.gamification.xpgivers;

import java.util.List;

import br.les.opus.gamification.domain.PerformedTask;

public abstract class XpRule {
	public abstract GivenXP xpFor(PerformedTask task, List<Object> arguments);
	
	@SuppressWarnings("unchecked")
	protected <T> T findArgumentByType(List<Object> arguments, Class<T> clazz) {
		for (Object object : arguments) {
			if (clazz.isInstance(object)) {
				return (T)object;
			}
		}
		return null;
	}
}
