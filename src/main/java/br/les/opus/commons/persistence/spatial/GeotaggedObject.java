package br.les.opus.commons.persistence.spatial;

import com.vividsolutions.jts.geom.Point;

public interface GeotaggedObject {

	Point getLocation();
}
