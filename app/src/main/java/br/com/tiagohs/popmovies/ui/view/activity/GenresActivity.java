package br.com.tiagohs.popmovies.ui.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.ui.callbacks.GenresCallbacks;
import br.com.tiagohs.popmovies.ui.view.fragment.GenresFragment;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;

public class GenresActivity extends BaseActivity implements GenresCallbacks {

    public static Intent newIntent(Context context) {
        return new Intent(context, GenresActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActivityTitle(getString(R.string.genres_title));
        startFragment(R.id.content_fragment, GenresFragment.newInstance());
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
