package br.les.opus.commons.geojson;

import java.util.ArrayList;
import java.util.List;

public class FeatureCollection extends GeoJsonObject {

	private List<Feature> features;
	
	public FeatureCollection() {
		features = new ArrayList<Feature>();
	}
	
	public void addFeature(Feature feature) {
		this.features.add(feature);
	}
	
	public List<Feature> getFeatures() {
		return features;
	}
	
	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
	
	@Override
	public String getType() {
		return "FeatureCollection";
	}
}
