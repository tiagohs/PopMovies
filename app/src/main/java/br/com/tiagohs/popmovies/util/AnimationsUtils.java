package br.com.tiagohs.popmovies.util;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

public class AnimationsUtils {

    public static Animation createFadeInAnimation(int duration) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(duration);

        return fadeIn;
    }

    public static Animation createFadeOutAnimation(int duration) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(duration);
        fadeOut.setDuration(duration);

        return fadeOut;
    }

    public static void creatScaleUpAnimation(View view) {

        view.animate()
                    .setStartDelay(100 * 10)
                    .setInterpolator(new AccelerateInterpolator())
                    .alpha(1)
                    .scaleX(1)
                    .scaleY(1);


    }
}
