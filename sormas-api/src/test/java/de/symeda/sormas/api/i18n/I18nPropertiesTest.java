package de.symeda.sormas.api.i18n;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

<<<<<<< HEAD
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.junit.Test;

import de.symeda.sormas.api.Language;
import de.symeda.sormas.api.i18n.I18nProperties.UTF8Control;

public class I18nPropertiesTest {

	@Test
	public void testControlToBundleName() {
		UTF8Control control = new UTF8Control();
		assertThat(control.toBundleName("captions", Locale.ROOT), is("captions"));
		assertThat(control.toBundleName("captions", new Locale("en")), is("captions_en"));
		assertThat(control.toBundleName("captions", new Locale("en", "NG")), is("captions_en-NG"));
	}
	
	@Test
	public void testUTF8Control() throws Exception {

		UTF8Control control = new UTF8Control();
		
		{
			Locale locale = Locale.ROOT;
			ResourceBundle bundle = control.newBundle("captions", locale, "java.properties", getClass().getClassLoader(), true);
			Set<String> keys = bundle.keySet();
			assertThat("Root Language", keys, not(empty()));
		}
		
		for (Language l : Language.values()) {
			ResourceBundle bundle = control.newBundle("captions", l.getLocale(), "java.properties", getClass().getClassLoader(), true);
			
			Set<String> keys = bundle.keySet();
			if (l.equals(Language.EN)) {
				assertThat(l.name(), keys, empty());
			} else {
				assertThat(l.name(), keys, not(empty()));
			}
		}
=======
import java.util.ResourceBundle;
import java.util.Set;

import org.junit.Test;

import de.symeda.sormas.api.Language;
import de.symeda.sormas.api.i18n.I18nProperties.UTF8Control;

public class I18nPropertiesTest {

	@Test
	public void testNormalizeCountryCode() throws Exception {
		assertThat(I18nProperties.UTF8Control.normalizeCountryCode("captions_en"), is("captions_en"));
		assertThat(I18nProperties.UTF8Control.normalizeCountryCode("captions_en-ng"), is("captions_en-NG"));
	}
	
	@Test
	public void testUTF8Control() throws Exception {
		
		ResourceBundle deDeBundle = new UTF8Control().newBundle("captions", Language.DE.getLocaleWithCountryCode(), "java.properties", getClass().getClassLoader(), true);
		Set<String> deDeKeys = deDeBundle.keySet();
		assertThat(deDeKeys, not(empty()));
>>>>>>> branch 'development' of https://github.com/Fede923/SORMAS-Project
	}
}
