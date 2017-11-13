package br.les.opus.dengue.core.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.les.opus.dengue.core.json.View;

import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "vote_poi")
public class PoiVote extends Vote {

	@ManyToOne
	@JsonView(View.Detail.class)
	@JoinColumn(name = "poi_id")
	private PointOfInterest poi;

	public PointOfInterest getPoi() {
		return poi;
	}

	public void setPoi(PointOfInterest poi) {
		this.poi = poi;
	}

	@Override
	public String toString() {
		return "PoiVote [poi=" + poi + ", isUpVote()=" + isUpVote()
				+ ", getUser()=" + getUser() + "]";
	}

	
}
