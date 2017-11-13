package br.les.opus.dengue.core.i18n;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class InternationalizerFactory {
	
	@Resource(name="collectionInternationalizer")
	private Internationalizer collectionInzer;
	
	@Resource(name="pagedResourcesInternationalizer")
	private Internationalizer pagedResourceInzer;
	
	@Resource(name="mappingJacksonValueInternationalizer")
	private Internationalizer mappingJacksonValueInzer;
	
	@Resource(name="objectInternationalizer")
	private Internationalizer objectInzer;
	
	private List<Internationalizer> internationalizers;
	
	private List<Internationalizer> getInternationalizers() {
		if (internationalizers == null) {
			internationalizers = new ArrayList<Internationalizer>();
			internationalizers.add(collectionInzer);
			internationalizers.add(pagedResourceInzer);
			internationalizers.add(mappingJacksonValueInzer);
			internationalizers.add(objectInzer);
		}
		return internationalizers;
	}

	public Internationalizer create(Object obj) {
		List<Internationalizer> internationalizers = this.getInternationalizers();
		for (Internationalizer internationalizer : internationalizers) {
			if (internationalizer.canInternationalize(obj)) {
				return internationalizer;
			}
		}
		return null;
	}
}
