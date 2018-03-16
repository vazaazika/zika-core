package br.les.opus.dengue.core.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vividsolutions.jts.geom.Point;

import br.les.opus.auth.core.domain.User;
import br.les.opus.commons.persistence.IdAware;

@Entity
@Table(name = "poi_status_update")
public class PoiStatusUpdate implements IdAware<Long> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "generator")
	@SequenceGenerator(name = "generator", sequenceName = "SQ_PK_POI_STATUS_UPDATE")
	private Long id;
	
	@JsonIgnore
	@NotNull
	@ManyToOne
	@JoinColumn(name = "poi_id", nullable=false)
	private PointOfInterest poi;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "type_id", nullable=false)
	private PoiStatusUpdateType type;
	
	@ManyToOne
	@JoinColumn(name = "user_id", nullable=false)
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Column(columnDefinition="Geometry")
	@Type(type="org.hibernate.spatial.GeometryType")
    private Point userLocation;
	
	public PoiStatusUpdate() {
		this.date = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PointOfInterest getPoi() {
		return poi;
	}

	public void setPoi(PointOfInterest poi) {
		this.poi = poi;
	}

	public PoiStatusUpdateType getType() {
		return type;
	}

	public void setType(PoiStatusUpdateType type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Point getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(Point userLocation) {
		this.userLocation = userLocation;
	}
	
}
