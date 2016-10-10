package br.com.tiagohs.popmovies.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;

public class ViewUtils {

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

    public static List<String> createAreasAtuacoesPerson(List<CreditMovieBasic> personsMovies) {
        List<String> areas = new ArrayList<>();

        for (CreditMovieBasic movie : personsMovies) {
            if (!areas.contains(movie.getDepartment()) && movie.getDepartment() != null) {
                if (!areas.contains(movie.getDepartment()))
                    areas.add(movie.getDepartment());
            }
        }

        return areas;
    }

}
