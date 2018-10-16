/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.les.opus.commons.persistence.filtering;

import br.les.opus.commons.persistence.date.IsoDateFormat;
import br.les.opus.dengue.core.domain.PointOfInterest;
import br.les.opus.gamification.domain.Badge;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author edge
 */
public class FieldValueConverterTest {
	
	public FieldValueConverterTest() {
	}
	
	@Before
	public void setUp() {
	}

	@Test
	public void testConvert() {
		FieldValueConverter converter = new FieldValueConverter();
		PointOfInterest poi = new PointOfInterest();
		poi.setAddress("Testando String");
		Date date = Date.from(LocalDate.of(2018, 1, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		assertEquals("Testando String", converter.convert("address", "Testando String", PointOfInterest.class));
		assertEquals(date, converter.convert("date", new IsoDateFormat().format(date), PointOfInterest.class));
		assertEquals(2L, converter.convert("id", "2", PointOfInterest.class));
		assertEquals(true, converter.convert("published", "true", PointOfInterest.class));
		assertEquals(4, converter.convert("upVoteCount", "4", PointOfInterest.class));
		assertEquals(0.6f, converter.convert("completion", "0.6", Badge.class));
	}
	
}
