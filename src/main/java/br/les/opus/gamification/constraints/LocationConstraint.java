package br.les.opus.gamification.constraints;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.Geometry;

import br.les.opus.gamification.constraints.checkers.LocationConstraintChecker;

@Entity
@Table(name = "game_assignment_constraint_location")
@PrimaryKeyJoinColumn(name = "constraint_id")
public class LocationConstraint extends AssignmentConstraint {
	
	@JsonIgnore
	@Column(columnDefinition="Geometry", nullable=false)
	@Type(type="org.hibernate.spatial.GeometryType")
	private Geometry geofence;

	@Override
	public Class<?> getCheckerClass() {
		return LocationConstraintChecker.class;
	}

	public Geometry getGeofence() {
		return geofence;
	}

	public void setGeofence(Geometry geofence) {
		this.geofence = geofence;
	}

}
