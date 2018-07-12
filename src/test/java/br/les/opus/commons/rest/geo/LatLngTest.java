/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.les.opus.commons.rest.geo;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author edge
 */
public class LatLngTest {
	
	public LatLngTest() {
	}
	
	@Before
	public void setUp() {
	}

	@Test
	public void testToPoint() {
		
		LatLng latLng = new LatLng();
		latLng.setLat(-9d);
		latLng.setLng(-10d);
		
		GeometryFactory factory = new GeometryFactory();
		Point point = factory.createPoint(new Coordinate(-10, -9));
		
		assertEquals(point, latLng.toPoint());
		
		LatLng newLatLng = new LatLng(point);
		assertEquals(-9d, newLatLng.getLat(), 0.00001);
		assertEquals(-10d, newLatLng.getLng(), 0.00001);
	}

	
}
