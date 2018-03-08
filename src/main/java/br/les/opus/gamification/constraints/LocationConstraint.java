package br.les.opus.gamification.constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import br.les.opus.gamification.domain.PerformedTask;

@Entity
@Table(name = "game_assignment_constraint_location")
@PrimaryKeyJoinColumn(name = "constraint_id")
public class LocationConstraint extends AssignmentConstraint {
	
	@Column(columnDefinition="Geometry", nullable=false)
	@Type(type="org.hibernate.spatial.GeometryType")
	private Geometry geofence;

	@Override
	public boolean isSatisfied(PerformedTask performedTask) {
		Point location = performedTask.getLocation();
		return location != null && geofence.contains(location);
	}

}
