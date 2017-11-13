package br.les.opus.dengue.core.i18n;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MappingJacksonValueInternationalizer extends AbstractInternationalizer {

	@Override
	public boolean canInternationalize(Object object) {
		return object instanceof MappingJacksonValue;
	}

	@Override
	public void internationalize(Object obj) throws I18nExpection {
		MappingJacksonValue responseWrapper = (MappingJacksonValue)obj;
		Object toInternationalize = responseWrapper.getValue();
		if (toInternationalize != null) {
			super.internationalize(responseWrapper.getValue());
		}
	}
}
