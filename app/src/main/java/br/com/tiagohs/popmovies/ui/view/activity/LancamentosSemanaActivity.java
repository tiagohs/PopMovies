package br.com.tiagohs.popmovies.ui.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.ui.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.ui.view.fragment.LancamentosSemanaFragment;

public class LancamentosSemanaActivity extends BaseActivity implements ListMoviesCallbacks {
    public static Intent newIntent(Context context) {
        return new Intent(context, LancamentosSemanaActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startFragment(R.id.content_fragment, LancamentosSemanaFragment.newInstance());

        setActivityTitle(getString(R.string.title_activity_lancamentos_semana));
    }


    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_default;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    protected int getMenuLayoutID() {
        return R.menu.menu_principal;
    }

    @Override
    public void onMovieSelected(int movieID, ImageView imageView) {
        ViewUtils.startMovieActivityWithTranslation(this, movieID, imageView, getString(R.string.poster_movie));
    }
}
