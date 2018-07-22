package br.com.tiagohs.popmovies.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.DiscoverDTO;
import br.com.tiagohs.popmovies.model.dto.ItemListDTO;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.ui.adapters.ListWordsAdapter;
import br.com.tiagohs.popmovies.ui.callbacks.ListWordsCallbacks;
import br.com.tiagohs.popmovies.ui.view.activity.GenresActivity;
import br.com.tiagohs.popmovies.ui.view.activity.LancamentosSemanaActivity;
import br.com.tiagohs.popmovies.ui.view.activity.ListsDefaultActivity;
import br.com.tiagohs.popmovies.util.DateUtils;
import br.com.tiagohs.popmovies.util.LocaleUtils;
import br.com.tiagohs.popmovies.util.enumerations.ItemType;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements ListWordsCallbacks {
    private static final int ID_KEYWORD_BRAZILIAN = 983;

    @BindView(R.id.destaques_keywords_recycler_view)    RecyclerView mDestaquesKeywoedsRecyclerView;
    @BindView(R.id.adView)                              AdView mAdView;

    private DiscoverDTO mEmBreveDiscoverDTO;
    private DiscoverDTO mEmCartazDiscoverDTO;
    private DiscoverDTO mPopularesDiscoverDTO;
    private DiscoverDTO mEstreiasDiscoverDTO;

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

        mAdView.loadAd(new AdRequest.Builder().build());
    }

    private void initDestaques() {
        mDestaquesKeywoedsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        mDestaquesKeywoedsRecyclerView.setAdapter(new ListWordsAdapter(getActivity(), createDestaqueItems(), this, ItemType.KEYWORD, R.layout.item_destaques_words_home));
    }

    public List<ItemListDTO> createDestaqueItems() {
        List<ItemListDTO> listDTOs = new ArrayList<>();
        DiscoverDTO discoverDTO;

        listDTOs.add(new ItemListDTO(getString(R.string.genres_title), Sort.GENEROS));

        listDTOs.add(new ItemListDTO(ID_KEYWORD_BRAZILIAN, getString(R.string.brasileiros), Sort.KEYWORDS, new DiscoverDTO()));

        discoverDTO = new DiscoverDTO();
        discoverDTO.setReleaseDateGte(DateUtils.getDateBefore(365));
        discoverDTO.setReleaseDateLte(DateUtils.getDateToday());
        discoverDTO.setVoteAvaregeGte(String.valueOf(6.5));
        discoverDTO.setSortBy("popularity.desc");

        listDTOs.add(new ItemListDTO(getString(R.string.destaques_do_ano), Sort.DISCOVER, discoverDTO));

        discoverDTO = new DiscoverDTO();
        discoverDTO.setVoteAvaregeGte(String.valueOf(6.5));
        listDTOs.add(new ItemListDTO(getString(R.string.mais_bem_avaliados), Sort.DISCOVER, discoverDTO));

        discoverDTO = new DiscoverDTO();
        discoverDTO.setSortBy("revenue.desc");
        listDTOs.add(new ItemListDTO(getString(R.string.campeoes_bilheteria), Sort.DISCOVER, discoverDTO));

        return listDTOs;
    }

    private Fragment createPopularesFragment() {
        mPopularesDiscoverDTO = new DiscoverDTO();

        mPopularesDiscoverDTO.setSortBy("popularity.desc");

        return ListMoviesDefaultFragment.newInstance(Sort.DISCOVER,
                R.layout.item_similares_movie, R.layout.fragment_list_movies_default_no_pull,
                mPopularesDiscoverDTO,
                ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false));
    }

    private Fragment createEmCartazFragment() {
        mEmCartazDiscoverDTO = new DiscoverDTO();

        mEmCartazDiscoverDTO.setReleaseDateGte(DateUtils.getDateBefore(31));
        mEmCartazDiscoverDTO.setReleaseDateLte(DateUtils.getDateToday());
        mEmCartazDiscoverDTO.setRegion(LocaleUtils.getLocaleCountryISO());
        mEmCartazDiscoverDTO.setWithReleaseType("2|3");
        mEmCartazDiscoverDTO.setSortBy("popularity.desc");

        return ListMoviesDefaultFragment.newInstance(Sort.DISCOVER, R.layout.item_similares_movie, R.layout.fragment_list_movies_default_no_pull, mEmCartazDiscoverDTO, ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false));
    }

    private Fragment createEmBreveFragment() {
        mEmBreveDiscoverDTO = new DiscoverDTO();

        mEmBreveDiscoverDTO.setPrimaryReleaseDateGte(DateUtils.getDateAfter(7));
        mEmBreveDiscoverDTO.setSortBy("popularity.desc");

        return ListMoviesDefaultFragment.newInstance(Sort.DISCOVER, R.layout.item_similares_movie, R.layout.fragment_list_movies_default_no_pull, mEmBreveDiscoverDTO, ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false));
    }

    private Fragment createEstreiasFragment() {
        mEstreiasDiscoverDTO = new DiscoverDTO();

        mEstreiasDiscoverDTO.setReleaseDateGte(DateUtils.getDateDayWeek(Calendar.SUNDAY));
        mEstreiasDiscoverDTO.setReleaseDateLte(DateUtils.getDateDayWeek(Calendar.SATURDAY));
        mEstreiasDiscoverDTO.setRegion(LocaleUtils.getLocaleCountryISO());
        mEstreiasDiscoverDTO.setWithReleaseType("2|3");
        mEstreiasDiscoverDTO.setSortBy("popularity.desc");

        return ListMoviesDefaultFragment.newInstance(Sort.DISCOVER, R.layout.item_similares_movie, R.layout.fragment_list_movies_default_no_pull, mEstreiasDiscoverDTO, ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false));
    }

    private void initMovies() {
        startFragment(R.id.container_em_breve_movies, createEmBreveFragment());
        startFragment(R.id.container_em_cartaz_movies, createEmCartazFragment());
        startFragment(R.id.container_estreia_movies, createEstreiasFragment());
        startFragment(R.id.container_popular_movies, createPopularesFragment());
    }

    @OnClick(R.id.movie_em_breve_riple)
    public void onClickEmBreve() {
        startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.em_breve), Sort.DISCOVER, R.layout.item_list_movies, ListType.MOVIES), mEmBreveDiscoverDTO));
    }

    @OnClick(R.id.movie_em_cartaz_riple)
    public void onClickEmCartaz() {
        startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.em_cartaz), Sort.DISCOVER, R.layout.item_list_movies, ListType.MOVIES), mEmCartazDiscoverDTO));
    }

    @OnClick(R.id.movie_estreias_semana_riple)
    public void onClickEstreias() {
        startActivity(LancamentosSemanaActivity.newIntent(getActivity()));
    }

    @OnClick(R.id.movie_populares_riple)
    public void onClickPopulares() {
        startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, getString(R.string.populares), Sort.DISCOVER, R.layout.item_list_movies, ListType.MOVIES), mPopularesDiscoverDTO));
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
                startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(0, item.getNameItem(), Sort.DISCOVER, R.layout.item_list_movies, ListType.MOVIES), item.getDiscoverDTO()));
                break;
            case GENEROS:
                startActivity(GenresActivity.newIntent(getActivity()));
                break;
            case KEYWORDS:
                startActivity(ListsDefaultActivity.newIntent(getContext(), new ListActivityDTO(item.getItemID(), getString(R.string.keywords), item.getNameItem(), Sort.KEYWORDS, R.layout.item_list_movies, ListType.MOVIES), new DiscoverDTO()));
                break;
        }
    }
}
