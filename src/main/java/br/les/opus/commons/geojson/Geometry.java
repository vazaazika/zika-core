package br.les.opus.commons.geojson;

import java.util.ArrayList;
import java.util.List;

public abstract class Geometry extends GeoJsonObject {

	private List<Double> coordinates;
	
	public Geometry() {
		this.coordinates = new ArrayList<Double>();
	}
	
	public void addValue(Double value) {
		this.coordinates.add(value);
	}

	public List<Double> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Double> coordinates) {
		this.coordinates = coordinates;
	}
	
}
