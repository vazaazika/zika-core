package br.les.opus.dengue.core.i18n;

import java.util.Collection;

import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PagedResourcesInternationalizer extends AbstractInternationalizer {

	@Override
	public boolean canInternationalize(Object object) {
		return object instanceof PagedResources;
	}

	@Override
	public void internationalize(Object obj) throws I18nExpection {
		PagedResources<?> paged = (PagedResources<?>)obj;
		Collection<?> collection = paged.getContent();
		for (Object resourceObject : collection) {
			Resource<?> resource = (Resource<?>)resourceObject;
			super.internationalize(resource.getContent());
		}
	}
}
