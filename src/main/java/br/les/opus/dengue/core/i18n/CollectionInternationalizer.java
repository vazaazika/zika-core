package br.les.opus.dengue.core.i18n;

import java.util.Collection;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CollectionInternationalizer extends AbstractInternationalizer {

	@Override
	public boolean canInternationalize(Object object) {
		return object instanceof Collection<?>;
	}

	@Override
	public void internationalize(Object obj) throws I18nExpection {
		Collection<?> collection = (Collection<?>)obj;
		for (Object object : collection) {
			super.internationalize(object);
		}
	}
}
