package br.les.opus.gamification.constraints.checkers;

import org.springframework.stereotype.Component;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import br.les.opus.gamification.constraints.AssignmentConstraint;
import br.les.opus.gamification.constraints.LocationConstraint;
import br.les.opus.gamification.domain.PerformedTask;

@Component
public class LocationConstraintChecker implements ConstraintChecker {

	@Override
	public Integer completedWork(AssignmentConstraint constraint, PerformedTask performedTask) {
		LocationConstraint locationConstraint = (LocationConstraint)constraint;
		Point location = performedTask.getLocation();
		Geometry geofence = locationConstraint.getGeofence();
		if (location != null && geofence.contains(location)) {
			return constraint.getWorkload();
		}
		return 0;
	}
}
