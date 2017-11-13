package br.les.opus.dengue.core.i18n.providers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class MetaFieldProviderFactory {
	
	@Resource(name="fieldValueMetaFieldsProvider")
	private MetaFieldProvider fieldValueMetaFieldsProvider;
	
	@Resource(name="defaultMetaFieldProvider")
	private MetaFieldProvider defaultMetaFieldProvider;
	
	private List<MetaFieldProvider> providers;
	
	private List<MetaFieldProvider> getProviders() {
		if (providers == null) {
			providers = new ArrayList<>();
			providers.add(fieldValueMetaFieldsProvider);
			providers.add(defaultMetaFieldProvider);
		}
		return providers;
	}

	public MetaFieldProvider create(Object obj) {
		List<MetaFieldProvider> internationalizers = this.getProviders();
		for (MetaFieldProvider metafieldProvider : internationalizers) {
			if (metafieldProvider.canProvide(obj)) {
				return metafieldProvider;
			}
		}
		return null;
	}
}
