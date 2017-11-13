package br.les.opus.dengue.core.i18n.providers;

import java.util.List;

import br.les.opus.dengue.core.i18n.I18nExpection;
import br.les.opus.dengue.core.i18n.I18nMetaField;

public interface MetaFieldProvider {

	List<I18nMetaField> getMetaFields(Object obj) throws I18nExpection;
	
	boolean canProvide(Object obj);
	
}
