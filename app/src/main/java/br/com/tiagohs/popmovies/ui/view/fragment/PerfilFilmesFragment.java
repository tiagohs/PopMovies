package br.com.tiagohs.popmovies.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.DiscoverDTO;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.ui.contracts.PerfilFilmesContract;
import br.com.tiagohs.popmovies.ui.view.activity.ListsDefaultActivity;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import butterknife.BindView;
import butterknife.OnClick;

public class PerfilFilmesFragment extends BaseFragment implements PerfilFilmesContract.PerfilFilmesView {

    @BindView(R.id.perfil_no_movies_favorite)           TextView mNoMoviesFavorite;
    @BindView(R.id.perfil_no_movies_want_see)           TextView mNoMoviesWantSee;
    @BindView(R.id.perfil_no_movies_dont_want_see)      TextView mNoMoviesDontWantSee;
    @BindView(R.id.perfil_no_movies_watched)            TextView mNoMoviesWatched;

    @Inject
    PerfilFilmesContract.PerfilFilmesPresenter mPresenter;

    public static PerfilFilmesFragment newInstance() {
        PerfilFilmesFragment perfilFilmesFragment = new PerfilFilmesFragment();
        return perfilFilmesFragment;
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_perfil_filmes;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getApplicationComponent().inject(this);

        mPresenter.onBindView(this);
        mPresenter.updateMoviesContainers(PrefsUtils.getCurrentProfile(getContext()).getProfileID());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onUnbindView();
    }

    public void hasMovies(boolean hasMovies, int idContainer, Fragment fragment, TextView textView) {
        if (hasMovies)
            startFragment(idContainer, fragment);
        else
            textView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.perfil_quero_ver_riple)
    public void onClickQueroVer() {
        startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.want_see), Sort.QUERO_VER, R.layout.item_list_movies, ListType.MOVIES), new DiscoverDTO()));
    }

    @OnClick(R.id.perfil_nao_quero_ver_riple)
    public void onClickNaoQueroVer() {
        startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.dont_want_see), Sort.NAO_QUERO_VER, R.layout.item_list_movies, ListType.MOVIES), new DiscoverDTO()));
    }

    @OnClick(R.id.perfil_assistidos_riple)
    public void onClickAssistidos() {
        startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.assistidos), Sort.ASSISTIDOS, R.layout.item_list_movies, ListType.MOVIES), new DiscoverDTO()));
    }

    @OnClick(R.id.perfil_favoritos_riple)
    public void onClickFavoritos() {
        startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.favoritos), Sort.FAVORITE, R.layout.item_list_movies, ListType.MOVIES), new DiscoverDTO()));
    }

    @Override
    public void hasMoviesWantSee(boolean hasMoviesWantSee) {
        hasMovies(hasMoviesWantSee, R.id.fragment_perfil_quero_ver,  ListMoviesDefaultFragment.newInstance(Sort.QUERO_VER, R.layout.item_similares_movie, R.layout.fragment_list_movies_default_no_pull, new DiscoverDTO(), ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false)), mNoMoviesWantSee);
    }

    @Override
    public void hasMoviesDontWantSee(boolean hasMoviesDontWantSee) {
        hasMovies(hasMoviesDontWantSee, R.id.fragment_perfil_nao_quero_ver,  ListMoviesDefaultFragment.newInstance(Sort.NAO_QUERO_VER, R.layout.item_similares_movie, R.layout.fragment_list_movies_default_no_pull, new DiscoverDTO(), ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false)), mNoMoviesDontWantSee);
    }

    @Override
    public void hasMoviesFavorite(boolean hasMoviesFavorite) {
        hasMovies(hasMoviesFavorite, R.id.fragment_perfil_favoritos,  ListMoviesDefaultFragment.newInstance(Sort.FAVORITE, R.layout.item_similares_movie, R.layout.fragment_list_movies_default_no_pull, new DiscoverDTO(), ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false)), mNoMoviesFavorite);
    }

    @Override
    public void hasMoviesWatched(boolean hasMoviesWatched) {
        hasMovies(hasMoviesWatched, R.id.fragment_perfil_assistidos,  ListMoviesDefaultFragment.newInstance(0, Sort.ASSISTIDOS, R.layout.item_list_movies, R.layout.fragment_list_movies_default_no_pull, new DiscoverDTO(), ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns))), mNoMoviesWatched);
    }

    @Override
    public void setProgressVisibility(int visibityState) {}
}
