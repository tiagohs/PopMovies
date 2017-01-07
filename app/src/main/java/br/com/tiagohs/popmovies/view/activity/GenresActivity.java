package br.com.tiagohs.popmovies.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.callbacks.GenresCallbacks;
import br.com.tiagohs.popmovies.view.fragment.GenresFragment;

public class GenresActivity extends BaseActivity implements GenresCallbacks {

    public static Intent newIntent(Context context) {
        return new Intent(context, GenresActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToolbar.setTitle("Gêneros");

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_fragment);

        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.content_fragment, GenresFragment.newInstance())
                    .commit();
        }
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    protected int getMenuLayoutID() {
        return 0;
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_genres;
    }

    @Override
    public void onGenreSelected(Genre genre) {
        startActivity(ListsDefaultActivity.newIntent(this, new ListActivityDTO(genre.getId(), genre.getName(), Sort.GENEROS, R.layout.item_list_movies, ListType.MOVIES)));
    }
}
