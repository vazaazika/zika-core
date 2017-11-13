package br.les.opus.dengue.core.i18n;

public interface Internationalizer {

	boolean canInternationalize(Object object);
	
	void internationalize(Object object) throws I18nExpection;
}
