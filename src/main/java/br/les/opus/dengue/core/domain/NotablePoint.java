package br.les.opus.dengue.core.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Maps to a view on the database representing all points of any source used by us: POI, Twitter or Instagram
 * @author Diego Cedrim
 */
@Entity
@Table(name = "all_points", schema = "public")
public class NotablePoint {

	@Id
	private String id;
	
	private String source;
	
	private Double latitude;
	
	private Double longitude;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
}
