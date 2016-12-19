package br.com.tiagohs.popmovies.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ItemListDTO;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.enumerations.ItemType;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Param;
import br.com.tiagohs.popmovies.util.enumerations.ParamSortBy;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.activity.GenresActivity;
import br.com.tiagohs.popmovies.view.activity.ListsDefaultActivity;
import br.com.tiagohs.popmovies.view.adapters.ListWordsAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ListWordsCallbacks;
import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements ListWordsCallbacks {
    private static final int ID_KEYWORD_BRAZILIAN = 983;

    @BindView(R.id.destaques_keywords_recycler_view)    RecyclerView mDestaquesKeywoedsRecyclerView;

    private Map<String, String> mEmBreveParameters;
    private Map<String, String> mEmCartazParameters;
    private Map<String, String> mPopularesParameters;
    private Map<String, String> mEstreiasParameters;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_home;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        initMovies();
        initDestaques();
    }

    private void initDestaques() {
        mDestaquesKeywoedsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        mDestaquesKeywoedsRecyclerView.setAdapter(new ListWordsAdapter(getActivity(), createDestaqueItems(), this, ItemType.KEYWORD, R.layout.item_destaques_words_home));
    }

    public List<ItemListDTO> createDestaqueItems() {
        List<ItemListDTO> listDTOs = new ArrayList<>();
        Map<String, String> parameters;

        listDTOs.add(new ItemListDTO(getString(R.string.generos), Sort.GENEROS));

        parameters = new HashMap<>();
        parameters.put(Param.WITH_KEYWORDS.getParam(), String.valueOf(ID_KEYWORD_BRAZILIAN));
        parameters.put(Param.SORT_BY.getParam(), ParamSortBy.POPULARITY_DESC.getValue());
        listDTOs.add(new ItemListDTO(getString(R.string.nacionais), Sort.DISCOVER, parameters));

        parameters = new HashMap<>();
        parameters.put(Param.PRIMARY_RELEASE_YEAR.getParam(), MovieUtils.getCurrentYear());
        parameters.put(Param.SORT_BY.getParam(), ParamSortBy.POPULARITY_DESC.getValue());
        listDTOs.add(new ItemListDTO(getString(R.string.destaques_do_ano), Sort.DISCOVER, parameters));

        parameters = new HashMap<>();
        parameters.put(Param.VOTE_AVERAGE_GTE.getParam(), String.valueOf(6.5));
        listDTOs.add(new ItemListDTO(getString(R.string.mais_bem_avaliados), Sort.DISCOVER, parameters));

        parameters = new HashMap<>();
        parameters.put(Param.SORT_BY.getParam(), ParamSortBy.REVENUE_DESC.getValue());
        listDTOs.add(new ItemListDTO(getString(R.string.campeoes_bilheteria), Sort.DISCOVER, parameters));

        return listDTOs;
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

    private Fragment createPopularesFragment() {
        mPopularesParameters = new HashMap<>();

        mPopularesParameters.put(Param.SORT_BY.getParam(), ParamSortBy.POPULARITY_DESC.getValue());

        return ListMoviesDefaultFragment.newInstance(Sort.DISCOVER, R.layout.item_similares_movie, mPopularesParameters, ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false));
    }

    private Fragment createEmCartazFragment() {
        mEmCartazParameters = new HashMap<>();

        mEmCartazParameters.put(Param.PRIMARY_RELEASE_DATE_GTE.getParam(), MovieUtils.getDateBefore(30));
        mEmCartazParameters.put(Param.PRIMARY_RELEASE_DATE_LTE.getParam(), MovieUtils.getDateToday());
        mEmCartazParameters.put(Param.SORT_BY.getParam(), ParamSortBy.POPULARITY_DESC.getValue());

        return ListMoviesDefaultFragment.newInstance(Sort.DISCOVER, R.layout.item_similares_movie, mEmCartazParameters, ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false));
    }

    private Fragment createEmBreveFragment() {
        mEmBreveParameters = new HashMap<>();

        mEmBreveParameters.put(Param.PRIMARY_RELEASE_DATE_GTE.getParam(), MovieUtils.getDateAfter(7));
        mEmBreveParameters.put(Param.SORT_BY.getParam(), ParamSortBy.POPULARITY_DESC.getValue());

        return ListMoviesDefaultFragment.newInstance(Sort.DISCOVER, R.layout.item_similares_movie, mEmBreveParameters, ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false));
    }

    private Fragment createEstreiasFragment() {
        mEstreiasParameters = new HashMap<>();

        mEstreiasParameters.put(Param.PRIMARY_RELEASE_DATE_GTE.getParam(), MovieUtils.getDateDayWeek(Calendar.THURSDAY));
        mEstreiasParameters.put(Param.PRIMARY_RELEASE_DATE_LTE.getParam(), MovieUtils.getDateDayWeek(Calendar.SATURDAY));
        mEstreiasParameters.put(Param.SORT_BY.getParam(), ParamSortBy.POPULARITY_DESC.getValue());

        return ListMoviesDefaultFragment.newInstance(Sort.DISCOVER, R.layout.item_similares_movie, mEstreiasParameters, ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false));
    }


    private void initMovies() {
        addFragment(R.id.container_em_breve_movies, createEmBreveFragment());
        addFragment(R.id.container_em_cartaz_movies, createEmCartazFragment());
        addFragment(R.id.container_estreia_movies, createEstreiasFragment());
        addFragment(R.id.container_popular_movies, createPopularesFragment());
    }

    @OnClick(R.id.movie_em_breve_riple)
    public void onClickEmBreve() {
        startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.em_breve), Sort.DISCOVER, R.layout.item_list_movies, ListType.MOVIES), mEmBreveParameters));
    }

    @OnClick(R.id.movie_em_cartaz_riple)
    public void onClickEmCartaz() {
        startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.em_cartaz), Sort.DISCOVER, R.layout.item_list_movies, ListType.MOVIES), mEmCartazParameters));
    }

    @OnClick(R.id.movie_estreias_semana_riple)
    public void onClickEstreias() {
        startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.estreias_semana), Sort.DISCOVER, R.layout.item_list_movies, ListType.MOVIES), mEstreiasParameters));
    }

    @OnClick(R.id.movie_populares_riple)
    public void onClickPopulares() {
        startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.populares), Sort.DISCOVER, R.layout.item_list_movies, ListType.MOVIES), mPopularesParameters));
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            initMovies();
            mSnackbar.dismiss();
            }
        };
    }

    @Override
    public void onItemSelected(ItemListDTO item, ItemType itemType) {

        switch (item.getTypeItem()) {
            case DISCOVER:
                startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, item.getNameItem(), Sort.DISCOVER, R.layout.item_list_movies, ListType.MOVIES), item.getParamenters()));
                break;
            case GENEROS:
                startActivity(GenresActivity.newIntent(getActivity()));
                break;
        }
    }
}
