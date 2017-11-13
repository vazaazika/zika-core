package br.les.opus.dengue.core.i18n.providers;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LanguageProvider {
	
	public String getUserLanguage() {
		Locale locale = LocaleContextHolder.getLocale();
		if (locale == null) {
			return Locale.ENGLISH.getLanguage();
		}
		String lang = locale.getLanguage();
		if (!lang.equals(Locale.ENGLISH.getLanguage()) 
				&& !lang.equals("pt")) {
			return Locale.ENGLISH.getLanguage();
		}
		return locale.getLanguage();
	}
}
