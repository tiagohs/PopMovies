package br.com.tiagohs.popmovies.view.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.view.fragment.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.view.fragment.MoviesByGenreFragment;

public class MoviesByGenreActivity extends BaseActivity implements ListMoviesCallbacks {
    private static final String ARG_GENRE_ID = "br.com.tiagohs.popmovies.genre";

    private Genre mGenre;

    public static Intent newIntent(Context context, Genre genre) {
        Intent intent = new Intent(context, MoviesByGenreActivity.class);
        intent.putExtra(ARG_GENRE_ID, genre);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGenre = (Genre) getIntent().getSerializableExtra(ARG_GENRE_ID);
        mToolbar.setTitle(mGenre.getName());
    }

    @Override
    protected void onStart() {
        super.onStart();

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_fragment);

        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.content_fragment, MoviesByGenreFragment.newInstance(mGenre.getId()))
                    .commit();
        }
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_movies_by_genre;
    }

    @Override
    public void onMovieSelected(int movieID, ImageView imageView) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions transitionActivityOptions = ActivityOptions
                    .makeSceneTransitionAnimation(this, imageView, getString(R.string.poster_movie));
            startActivity(MovieDetailActivity.newIntent(this, movieID), transitionActivityOptions.toBundle());
        } else {
            startActivity(MovieDetailActivity.newIntent(this, movieID));
        }

    }
}
