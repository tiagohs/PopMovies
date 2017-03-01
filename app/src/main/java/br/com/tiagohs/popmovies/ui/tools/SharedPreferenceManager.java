package br.com.tiagohs.popmovies.ui.tools;


import android.content.SharedPreferences;

import java.util.Locale;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.util.LocaleUtils;

public class SharedPreferenceManager  {

    public static final String PREFS_DEFAULT_LANGUAGE_KEY = "default_language_key";

    private SharedPreferences mSharedPreferences;

    @Inject
    public SharedPreferenceManager(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    public String getDefaultLanguage() {
        return mSharedPreferences.getString(PREFS_DEFAULT_LANGUAGE_KEY, LocaleUtils.getLocaleLanguageAndCountry());
    }

    public void setDefaultLanguage(Locale locale) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(PREFS_DEFAULT_LANGUAGE_KEY, LocaleUtils.getLocaleLanguageAndCountry(locale));
        editor.apply();
    }
}
