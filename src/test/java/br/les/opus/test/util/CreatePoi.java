package br.les.opus.test.util;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import br.les.opus.dengue.core.domain.Picture;
import br.les.opus.dengue.core.domain.PoiType;
import br.les.opus.dengue.core.domain.PointOfInterest;
import br.les.opus.dengue.core.fields.Field;
import br.les.opus.dengue.core.fields.FieldType;

/**
 * Create a default {@link PointOfInterest}
 * @author leonardo
 *
 */
public class CreatePoi {
	
	/**
	 * Return a Point of Interest
	 */
	public static PointOfInterest create(PoiType type) {
		PointOfInterest poi = new PointOfInterest();
		poi.setAddress("los angeles");
		poi.setDate(new GregorianCalendar().getTime());
		poi.setTitle("titulo");
		poi.setPublished(true);
		poi.setDescription("descricao");


		List<Field> fields = new ArrayList<Field>();
		Field f = new Field();
		f.setId(1L);
		f.setName("field");
		f.setHelpText("help");
		f.setRequired(true);
		FieldType fType = new FieldType();
		fType.setId(1L);
		fType.setName("fieldtype");
		f.setType(fType);
		fields.add(f);
		type.setFields(fields);
		
		Point point = new GeometryFactory().createPoint(new Coordinate(4.54, 4.5));

		poi.setLocation(point);

		List<Picture> pictures = new ArrayList<Picture>();
		poi.setPictures(pictures);
		poi.setType(type);
		
		return poi;
	}

}
