package br.com.tiagohs.popmovies.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.HashMap;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.data.repository.ProfileRepository;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.activity.ListsDefaultActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class PerfilFilmesFragment extends BaseFragment {

    @BindView(R.id.perfil_no_movies_favorite)
    TextView mNoMoviesFavorite;

    @BindView(R.id.perfil_no_movies_want_see)
    TextView mNoMoviesWantSee;

    @BindView(R.id.perfil_no_movies_dont_want_see)
    TextView mNoMoviesDontWantSee;

    @BindView(R.id.perfil_no_movies_watched)
    TextView mNoMoviesWatched;


    private ProfileRepository mProfileRepository;

    private long mProfileID;

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
        mProfileRepository = new ProfileRepository(getContext());

        mProfileID = PrefsUtils.getCurrentProfile(getContext()).getProfileID();

        hasMovies(mProfileRepository.hasMoviesWantSee(mProfileID), R.id.fragment_perfil_quero_ver,  ListMoviesDefaultFragment.newInstance(Sort.QUERO_VER, R.layout.item_similares_movie, R.layout.fragment_list_movies_default_no_pull, new HashMap<String, String>(), ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false)), mNoMoviesWantSee);
        hasMovies(mProfileRepository.hasMoviesDontWantSee(mProfileID), R.id.fragment_perfil_nao_quero_ver,  ListMoviesDefaultFragment.newInstance(Sort.NAO_QUERO_VER, R.layout.item_similares_movie, R.layout.fragment_list_movies_default_no_pull, new HashMap<String, String>(), ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false)), mNoMoviesDontWantSee);
        hasMovies(mProfileRepository.hasMoviesFavorite(mProfileID), R.id.fragment_perfil_favoritos,  ListMoviesDefaultFragment.newInstance(Sort.FAVORITE, R.layout.item_similares_movie, R.layout.fragment_list_movies_default_no_pull, new HashMap<String, String>(), ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false)), mNoMoviesFavorite);
        hasMovies(mProfileRepository.hasMoviesWatched(mProfileID), R.id.fragment_perfil_assistidos,  ListMoviesDefaultFragment.newInstance(0, Sort.ASSISTIDOS, R.layout.item_list_movies, R.layout.fragment_list_movies_default_no_pull, new HashMap<String, String>(), ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns))), mNoMoviesWatched);

    }

    public void hasMovies(boolean hasMovies, int idContainer, Fragment fragment, TextView textView) {
        if (hasMovies)
            addFragment(idContainer, fragment);
        else
            textView.setVisibility(View.VISIBLE);
    }

    private void addFragment(int id, Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        Fragment f = fm.findFragmentById(id);

        if (f == null) {
            fm.beginTransaction()
                    .add(id, fragment)
                    .commit();
        }

    }

    @OnClick(R.id.perfil_quero_ver_riple)
    public void onClickQueroVer() {
        if (mProfileRepository.hasMoviesWantSee(mProfileID))
            startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.quero_ver), Sort.QUERO_VER, R.layout.item_list_movies, ListType.MOVIES), new HashMap<String, String>()));
    }

    @OnClick(R.id.perfil_nao_quero_ver_riple)
    public void onClickNaoQueroVer() {
        if (mProfileRepository.hasMoviesDontWantSee(mProfileID))
            startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.dont_want_see), Sort.NAO_QUERO_VER, R.layout.item_list_movies, ListType.MOVIES), new HashMap<String, String>()));
    }

    @OnClick(R.id.perfil_assistidos_riple)
    public void onClickAssistidos() {
        if (mProfileRepository.hasMoviesWatched(mProfileID))
            startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.assistidos), Sort.ASSISTIDOS, R.layout.item_list_movies, ListType.MOVIES), new HashMap<String, String>()));
    }

    @OnClick(R.id.perfil_favoritos_riple)
    public void onClickFavoritos() {
        if (mProfileRepository.hasMoviesFavorite(mProfileID))
            startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.favoritos), Sort.FAVORITE, R.layout.item_list_movies, ListType.MOVIES), new HashMap<String, String>()));
    }
}
