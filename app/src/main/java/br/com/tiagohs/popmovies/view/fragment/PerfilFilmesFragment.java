package br.com.tiagohs.popmovies.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.HashMap;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.activity.ListsDefaultActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class PerfilFilmesFragment extends BaseFragment {

    public static PerfilFilmesFragment newInstance() {
        PerfilFilmesFragment perfilFilmesFragment = new PerfilFilmesFragment();
        return perfilFilmesFragment;
    }

    public PerfilFilmesFragment() {

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

        addFragment(R.id.container_perfil_favoritos, ListMoviesDefaultFragment.newInstance(Sort.FAVORITE, R.layout.item_similares_movie, new HashMap<String, String>(), ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false)));
        addFragment(R.id.container_perfil_assistidos, ListMoviesDefaultFragment.newInstance(0, Sort.ASSISTIDOS, R.layout.item_list_movies, new HashMap<String, String>(), ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns))));
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

    @OnClick(R.id.perfil_assistidos_riple)
    public void onClickAssistidos() {
        startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.assistidos), Sort.ASSISTIDOS, R.layout.item_list_movies, ListType.MOVIES), new HashMap<String, String>()));
    }

    @OnClick(R.id.perfil_favoritos_riple)
    public void onClickFavoritos() {
        startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.favoritos), Sort.FAVORITE, R.layout.item_list_movies, ListType.MOVIES), new HashMap<String, String>()));
    }
}
