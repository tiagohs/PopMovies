package br.com.tiagohs.popmovies.util;

import android.content.Context;

import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.ui.tools.ComplexPreferences;

public class PrefsUtils {
    public static final String USER_PREFS_ID = "user_prefs";
    public static final String PROFILE_PREFS_ID = "profile_prefs";
    public static final String PROFILE_ID_PREFS_ID = "profile_id_prefs";

    public static final String USER_PREFS_VALUE_ID = "current_user_value";
    public static final String PROFILE_PREFS_VALUE_ID = "current_profile_value";
    public static final String PROFILE_ID_PREFS_VALUE_ID = "current_profile_id_value";

    public static void setCurrentUser(UserDB currentUser, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, USER_PREFS_ID, 0);
        complexPreferences.putObject(USER_PREFS_VALUE_ID, currentUser);
        complexPreferences.commit();
    }

    public static UserDB getCurrentUser(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, USER_PREFS_ID, 0);
        return complexPreferences.getObject(USER_PREFS_VALUE_ID, UserDB.class);
    }

    public static void clearCurrentUser( Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, USER_PREFS_ID, 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }

    public static void setCurrentProfile(ProfileDB currentProfile, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, PROFILE_PREFS_ID, 0);
        complexPreferences.putObject(PROFILE_PREFS_VALUE_ID, currentProfile);
        complexPreferences.commit();
    }

    public static ProfileDB getCurrentProfile(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, PROFILE_PREFS_ID, 0);
        return complexPreferences.getObject(PROFILE_PREFS_VALUE_ID, ProfileDB.class);
    }

    public static void clearCurrentProfile(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, PROFILE_PREFS_ID, 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }

}
