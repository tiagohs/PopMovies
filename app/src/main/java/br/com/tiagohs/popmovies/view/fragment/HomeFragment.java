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
import br.com.tiagohs.popmovies.util.LocaleUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.enumerations.ItemType;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Param;
import br.com.tiagohs.popmovies.util.enumerations.ParamSortBy;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.activity.GenresActivity;
import br.com.tiagohs.popmovies.view.activity.LancamentosSemanaActivity;
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        listDTOs.add(new ItemListDTO(getString(R.string.genres_title), Sort.GENEROS));

        listDTOs.add(new ItemListDTO(ID_KEYWORD_BRAZILIAN, getString(R.string.brasileiros), Sort.KEYWORDS, new HashMap<String, String>()));

        parameters = new HashMap<>();
        parameters.put(Param.RELEASE_DATE_GTE.getParam(), MovieUtils.getDateBefore(365));
        parameters.put(Param.RELEASE_DATE_LTE.getParam(), MovieUtils.getDateToday());
        parameters.put(Param.VOTE_AVERAGE_GTE.getParam(), String.valueOf(6.5));
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

    private Fragment createPopularesFragment() {
        mPopularesParameters = new HashMap<>();

        mPopularesParameters.put(Param.SORT_BY.getParam(), ParamSortBy.POPULARITY_DESC.getValue());

        return ListMoviesDefaultFragment.newInstance(Sort.DISCOVER, R.layout.item_similares_movie, R.layout.fragment_list_movies_default_no_pull, mPopularesParameters, ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false));
    }

    private Fragment createEmCartazFragment() {
        mEmCartazParameters = new HashMap<>();

        mEmCartazParameters.put(Param.RELEASE_DATE_GTE.getParam(), MovieUtils.getDateBefore(31));
        mEmCartazParameters.put(Param.RELEASE_DATE_LTE.getParam(), MovieUtils.getDateToday());
        mEmCartazParameters.put(Param.RELEASE.getParam(), LocaleUtils.getLocaleCountryISO());
        mEmCartazParameters.put(Param.WITH_RELEASE_TYPE.getParam(), "2|3");
        mEmCartazParameters.put(Param.SORT_BY.getParam(), ParamSortBy.POPULARITY_DESC.getValue());

        return ListMoviesDefaultFragment.newInstance(Sort.DISCOVER, R.layout.item_similares_movie, R.layout.fragment_list_movies_default_no_pull, mEmCartazParameters, ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false));
    }

    private Fragment createEmBreveFragment() {
        mEmBreveParameters = new HashMap<>();

        mEmBreveParameters.put(Param.PRIMARY_RELEASE_DATE_GTE.getParam(), MovieUtils.getDateAfter(7));
        mEmBreveParameters.put(Param.SORT_BY.getParam(), ParamSortBy.POPULARITY_DESC.getValue());

        return ListMoviesDefaultFragment.newInstance(Sort.DISCOVER, R.layout.item_similares_movie, R.layout.fragment_list_movies_default_no_pull, mEmBreveParameters, ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false));
    }

    private Fragment createEstreiasFragment() {
        mEstreiasParameters = new HashMap<>();

        mEstreiasParameters.put(Param.RELEASE_DATE_GTE.getParam(), MovieUtils.getDateDayWeek(Calendar.SUNDAY));
        mEstreiasParameters.put(Param.RELEASE_DATE_LTE.getParam(), MovieUtils.getDateDayWeek(Calendar.SATURDAY));
        mEstreiasParameters.put(Param.RELEASE.getParam(), LocaleUtils.getLocaleCountryISO());
        mEstreiasParameters.put(Param.WITH_RELEASE_TYPE.getParam(), "2|3");
        mEstreiasParameters.put(Param.SORT_BY.getParam(), ParamSortBy.POPULARITY_DESC.getValue());

        return ListMoviesDefaultFragment.newInstance(Sort.DISCOVER, R.layout.item_similares_movie, R.layout.fragment_list_movies_default_no_pull, mEstreiasParameters, ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false));
    }


    private void initMovies() {
        startFragment(R.id.container_em_breve_movies, createEmBreveFragment());
        startFragment(R.id.container_em_cartaz_movies, createEmCartazFragment());
        startFragment(R.id.container_estreia_movies, createEstreiasFragment());
        startFragment(R.id.container_popular_movies, createPopularesFragment());
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
        startActivity(LancamentosSemanaActivity.newIntent(getActivity()));
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
            case KEYWORDS:
                startActivity(ListsDefaultActivity.newIntent(getContext(), new ListActivityDTO(item.getItemID(), getString(R.string.keyword_name), item.getNameItem(), Sort.KEYWORDS, R.layout.item_list_movies, ListType.MOVIES), new HashMap<String, String>()));
                break;
        }
    }
}