package br.com.tiagohs.popmovies.util;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.ui.view.activity.MovieDetailActivity;

public class ViewUtils {

    public static void createToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void startMovieActivityWithTranslation(Activity context, int movieID, ImageView imageView, String posterTranslationID) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions transitionActivityOptions = ActivityOptions
                    .makeSceneTransitionAnimation(context, imageView, posterTranslationID);
            context.startActivity(MovieDetailActivity.newIntent(context, movieID), transitionActivityOptions.toBundle());
        } else {
            context.startActivity(MovieDetailActivity.newIntent(context, movieID));
        }
    }

    public static int getColorFromResource(Context context, int resourceID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return context.getColor(resourceID);
        else
            return context.getResources().getColor(resourceID);
    }

    public static Drawable getDrawableFromResource(Context context, int drawableID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return context.getDrawable(drawableID);
        else
            return context.getResources().getDrawable(drawableID);
    }

    public static Bitmap getBitmapFromView(LinearLayout view) {
        try {

            view.setDrawingCacheEnabled(true);

            view.measure(View.MeasureSpec.makeMeasureSpec(800, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(600, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

            view.buildDrawingCache(true);
            Bitmap returnedBitmap = Bitmap.createBitmap(view.getDrawingCache());

            //Define a bitmap with the same size as the view
            view.setDrawingCacheEnabled(false);

            return returnedBitmap;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void createRatingGadget(Context context, float rating, ProgressBar rankingProgress, int max) {

        if (rating < (max / 2))
            setRankingState(context, R.drawable.progress_circle_red, rankingProgress);
        else if (rating >= (max / 2) && rating < (max / 1.6))
            setRankingState(context, R.drawable.progress_circle_yellow, rankingProgress);
        else if (rating >= (max / 1.6) && rating <= max)
            setRankingState(context, R.drawable.progress_circle_green, rankingProgress);
        else
            setRankingState(context, R.color.secondary_text, rankingProgress);

        rankingProgress.setProgress(Math.round(rating));
        rankingProgress.setMax(max);

    }

    public static String createDefaultPersonBiography(String name, String areas, List<MovieListDTO> knowForMovies) {
        StringBuilder biography = new StringBuilder();

        biography.append(name + " is an ");
        biography.append(areas);

        if (!knowForMovies.isEmpty()) {
            biography.append(", known for ");

            for (MovieListDTO movie : knowForMovies) {
                biography.append("'" + movie.getMovieName() + "', ");
            }
        }

        biography.append(".");

        return biography.toString();
    }

    private static void setRankingState(Context context, int state, ProgressBar rankingProgress) {
        rankingProgress.setProgressDrawable(ViewUtils.getDrawableFromResource(context, state));
    }

}
