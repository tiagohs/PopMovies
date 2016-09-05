package br.com.tiagohs.popmovies.util;

import android.content.res.Resources;

import java.util.Locale;

/**
 * Created by Tiago Henrique on 04/09/2016.
 */
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

    public static String getLocaleLanguageName(String languageIso) {
        return getLocaleAtual().getDisplayLanguage(new Locale(languageIso));
    }

    public static String getLocaleLanguageName() {
        return getLocaleAtual().getDisplayLanguage();
    }

    public static String getLocaleCountryISO() {
        return getLocaleAtual().getCountry();
    }

    public static String getLocaleCountryName() {
        return getLocaleAtual().getDisplayCountry();
    }

    public static String getLocaleCountryName(String languageIso) {
        return getLocaleAtual().getDisplayCountry(new Locale(languageIso));
    }

    public static String getLocaleLanguageAndCountry() {
        return getLocaleLanguageISO() + "-" + getLocaleCountryName();
    }

}
