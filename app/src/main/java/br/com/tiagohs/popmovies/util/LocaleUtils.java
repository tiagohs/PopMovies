package br.com.tiagohs.popmovies.util;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

import br.com.tiagohs.popmovies.model.dto.LocaleDTO;

public class LocaleUtils {

    public static Locale getLocaleAtual() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            return Resources.getSystem().getConfiguration().getLocales().get(0);
        else
            return Resources.getSystem().getConfiguration().locale;
    }

    public static String getLocaleLanguageISO() {
        return getLocaleAtual().getLanguage();
    }

    public static String getLocaleLanguageISO(Locale locale) {
        return getLocaleAtual().getLanguage();
    }

    public static String getLocaleLanguageName(String languageIso) {
        return getLocaleAtual().getDisplayLanguage(new Locale(languageIso));
    }

    public static String getLocaleLanguageName() {
        return getLocaleAtual().getDisplayLanguage();
    }

    public static String getLocaleCountryISO() {
        return getLocaleAtual().getCountry();
    }

    public static String getLocaleCountryISO(Locale locale) {
        return locale.getCountry();
    }

    public static String getLocaleCountryName() {
        return getLocaleAtual().getDisplayCountry();
    }

    public static String getLocaleCountryName(String languageIso) {
        return getLocaleAtual().getDisplayCountry(new Locale(languageIso));
    }

    public static String getLocaleLanguageAndCountry() {
        return getLocaleLanguageISO() + "-" + getLocaleCountryISO();
    }

    public static String getLocaleLanguageAndCountry(Locale locale) {
        return getLocaleLanguageISO(locale) + "-" + getLocaleCountryISO(locale);
    }

    public static List<String> getAllCountrys() {
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }

        Collections.sort(countries);

        return countries;
    }

    public static List<LocaleDTO> getAllCountrysDTO() {
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<LocaleDTO> countries = new ArrayList<LocaleDTO>();

        LocaleDTO localeDTO;
        for (Locale locale : locales) {
            try {
                String country = locale.getDisplayCountry();
                String iso = locale.getISO3Country();
                String code = locale.getCountry();
                String name = locale.getDisplayCountry();
                if (country.trim().length() > 0 && !countries.contains(country) && !"".equals(iso) && !"".equals(code) && !"".equals(name)) {
                    localeDTO = new LocaleDTO(name, locale.getDisplayLanguage(), iso, locale.getISO3Language(), locale);
                    if (!countries.contains(localeDTO))
                        countries.add(localeDTO);
                }
            } catch (MissingResourceException e){

            }

        }

        Collections.sort(countries);

        return countries;
    }

}
