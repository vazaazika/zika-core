package br.les.opus.commons.persistence.spatial;

public class DistanceResult {
	
	private GeotaggedObject object;
	
	private Double distance;
	
	public DistanceResult() {

	}
	
	public DistanceResult(GeotaggedObject object, Double distance) {
		this.object = object;
		this.distance = distance;
	}

	public GeotaggedObject getObject() {
		return object;
	}

	public void setObject(GeotaggedObject object) {
		this.object = object;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
}
