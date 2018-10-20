package br.les.opus.gamification.xpgivers;

import java.util.List;

import br.les.opus.dengue.core.domain.PointOfInterest;
import br.les.opus.gamification.domain.PerformedTask;

public class CreatePoiXpRule extends XpRule {

	@Override
	public GivenXP xpFor(PerformedTask task, List<Object> arguments) {
		PointOfInterest poi = findArgumentByType(arguments, PointOfInterest.class);
		int xp = 125;
		
		return null;
	}

}
