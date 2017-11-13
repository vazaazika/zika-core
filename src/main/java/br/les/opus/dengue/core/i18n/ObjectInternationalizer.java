package br.les.opus.dengue.core.i18n;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ObjectInternationalizer extends AbstractInternationalizer {

	@Override
	public boolean canInternationalize(Object object) {
		return object instanceof Object;
	}

	@Override
	public void internationalize(Object obj) throws I18nExpection {
		super.internationalize(obj);
	}
}
