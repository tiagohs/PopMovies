package br.com.tiagohs.popmovies.util;

import android.content.Context;

import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.db.UserDB;

/**
 * Created by Tiago on 17/12/2016.
 */

public class PrefsUtils {

    public static void setCurrentUser(UserDB currentUser, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        complexPreferences.putObject("current_user_value", currentUser);
        complexPreferences.commit();
    }

    public static UserDB getCurrentUser(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        UserDB currentUser = complexPreferences.getObject("current_user_value", UserDB.class);
        return currentUser;
    }

    public static void clearCurrentUser( Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "user_prefs", 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }

    public static void setCurrentProfile(ProfileDB currentProfile, Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "profile_prefs", 0);
        complexPreferences.putObject("current_profile_value", currentProfile);
        complexPreferences.commit();
    }

    public static ProfileDB getCurrentProfile(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "profile_prefs", 0);
        ProfileDB currentProfile = complexPreferences.getObject("current_profile_value", ProfileDB.class);
        return currentProfile;
    }

    public static void clearCurrentProfile(Context ctx){
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(ctx, "profile_prefs", 0);
        complexPreferences.clearObject();
        complexPreferences.commit();
    }
}
