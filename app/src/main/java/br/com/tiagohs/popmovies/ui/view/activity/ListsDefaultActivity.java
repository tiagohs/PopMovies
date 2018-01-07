package br.com.tiagohs.popmovies.ui.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.DiscoverDTO;
import br.com.tiagohs.popmovies.model.dto.FilterValuesDTO;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.dto.PersonListDTO;
import br.com.tiagohs.popmovies.ui.callbacks.FiltersMoviesCallbacks;
import br.com.tiagohs.popmovies.ui.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.ui.callbacks.PersonCallbacks;
import br.com.tiagohs.popmovies.ui.view.fragment.FilterDialogFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.ListMoviesDefaultFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.ListPersonsDefaultFragment;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;

public class ListsDefaultActivity extends BaseActivity implements ListMoviesCallbacks, PersonCallbacks, FiltersMoviesCallbacks {
    private static final String TAG = ListsDefaultActivity.class.getSimpleName();

    private static final String ARG_FTO = "br.com.tiagohs.popmovies.dto";
    private static final String ARG_DISCOVER_DTO = "br.com.tiagohs.popmovies.discover_dto";
    private static final String ARG_PERSON_LIST = "br.com.tiagohs.popmovies.person_list";
    private static final String ARG_MOVIES_LIST = "br.com.tiagohs.popmovies.movies_list";

    public static final int LINEAR_LAYOUT = 0;
    public static final int GRID_LAYOUT = 1;
    public static final int STAGGERED = 2;

    private DiscoverDTO mDiscoverDTO;
    private DiscoverDTO mDiscoverFilterDTO;

    private List<PersonListDTO> mPersons;
    private List<MovieListDTO> mMovies;

    private Sort mOriginalSort;
    private ListActivityDTO mListActivityDTO;

    public static Intent newIntent(Context context, ListActivityDTO listDTO) {
        Intent intent = new Intent(context, ListsDefaultActivity.class);
        intent.putExtra(ARG_FTO, listDTO);
        return intent;
    }

    public static Intent newIntent(Context context, ListActivityDTO listDTO, DiscoverDTO discoverDTO) {
        Intent intent = new Intent(context, ListsDefaultActivity.class);
        intent.putExtra(ARG_FTO, listDTO);
        intent.putExtra(ARG_DISCOVER_DTO, discoverDTO);

        return intent;
    }

    public static Intent newIntent(Context context, ListActivityDTO listDTO, List<PersonListDTO> persons) {
        Intent intent = new Intent(context, ListsDefaultActivity.class);
        intent.putExtra(ARG_FTO, listDTO);
        intent.putParcelableArrayListExtra(ARG_PERSON_LIST, (ArrayList<PersonListDTO>) persons);

        return intent;
    }

    public static Intent newIntent(Context context, List<MovieListDTO> movies, ListActivityDTO listDTO) {
        Intent intent = new Intent(context, ListsDefaultActivity.class);
        intent.putExtra(ARG_FTO, listDTO);
        intent.putParcelableArrayListExtra(ARG_MOVIES_LIST, (ArrayList<MovieListDTO>) movies);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListActivityDTO = getIntent().getParcelableExtra(ARG_FTO);
        mDiscoverDTO = getIntent().getParcelableExtra(ARG_DISCOVER_DTO);
        mPersons = getIntent().getParcelableArrayListExtra(ARG_PERSON_LIST);
        mMovies =  getIntent().getParcelableArrayListExtra(ARG_MOVIES_LIST);

        setActivityTitle(mListActivityDTO.getNameActivity());
        setActivitySubtitle(mListActivityDTO.getSubtitleActivity());

        onUpdateUI(mDiscoverDTO);
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_default;
    }

    @Override
    protected int getMenuLayoutID() {

        if (EmptyUtils.isNotNull(mListActivityDTO.getSortList())) {
            if (mListActivityDTO.getSortList().equals(Sort.ASSISTIDOS) || mListActivityDTO.getSortList().equals(Sort.FAVORITE) ||
                    mListActivityDTO.getSortList().equals(Sort.QUERO_VER) || mListActivityDTO.getSortList().equals(Sort.NAO_QUERO_VER) ||
                    mListActivityDTO.getSortList().equals(Sort.LIST_DEFAULT))
                return 0;
        }

        return mListActivityDTO.getListType().equals(ListType.MOVIES) ? R.menu.menu_list_defult : R.menu.menu_principal;

    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUpdateUI(null == mDiscoverFilterDTO ? mDiscoverDTO : mDiscoverFilterDTO);
                mSnackbar.dismiss();
            }
        };
    }

    private void onUpdateUI(DiscoverDTO currentDiscoverDTO) {
        Fragment fragment = null;

        switch (mListActivityDTO.getListType()) {
            case MOVIES:
                if (null == mMovies)
                    fragment = ListMoviesDefaultFragment.newInstance(mListActivityDTO.getId(), mListActivityDTO.getSortList(), mListActivityDTO.getLayoutID(), R.layout.fragment_list_movies_default, currentDiscoverDTO, ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns)));
                else
                    fragment = ListMoviesDefaultFragment.newInstance(mListActivityDTO.getSortList(), mListActivityDTO.getLayoutID(), R.layout.fragment_list_movies_default, mMovies, ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns)));
                break;
            case PERSON:
                if (null == mPersons)
                    fragment = ListPersonsDefaultFragment.newInstance(mListActivityDTO.getSortList(), R.layout.item_person_default, ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns)));
                else
                    fragment = ListPersonsDefaultFragment.newInstance(mPersons, R.layout.item_person_default, ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns)));
                break;
        }

        startFragment(R.id.content_fragment, fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_filter:
                FilterDialogFragment filter = FilterDialogFragment.newInstance();
                filter.show(getSupportFragmentManager().beginTransaction(), getString(R.string.filter_dialog_title));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieSelected(int movieID, ImageView imageView) {
        startActivity(MovieDetailActivity.newIntent(this, movieID));
    }

    @Override
    public void onClickPerson(int personID) {
        startActivity(PersonDetailActivity.newIntent(this, personID));
    }

    @Override
    public void onFilterChanged(FilterValuesDTO filters) {
        mDiscoverFilterDTO = new DiscoverDTO();

        mDiscoverFilterDTO.setSortBy(filters.getSortBy());
        mDiscoverFilterDTO.setIncludeAdult(filters.isIncludeAdult());
        mDiscoverFilterDTO.setReleaseDateGte(filters.getPrimaryRelaseDateGte());
        mDiscoverFilterDTO.setReleaseDateLte(filters.getPrimaryRelaseDateLte());
        mDiscoverFilterDTO.setReleaseYear(filters.getReleaseYear());
        mDiscoverFilterDTO.setVoteAvaregeGte(filters.getVoteAverageGte());

        additionalSearchConfigurations();
        onUpdateUI(mDiscoverFilterDTO);
    }

    @Override
    public void onFilterReset() {
        mDiscoverFilterDTO = null;

        if (EmptyUtils.isNotNull(mOriginalSort)) {
            switch (mOriginalSort) {
                case GENEROS:
                case KEYWORDS:
                    mListActivityDTO.setSortList(mOriginalSort);
            }
        }

        onUpdateUI(mDiscoverDTO);
    }

    private void additionalSearchConfigurations() {
        mOriginalSort = mListActivityDTO.getSortList();

        switch (mListActivityDTO.getSortList()) {
            case GENEROS:
                mDiscoverFilterDTO.setWithGenres(mListActivityDTO.getId());
                mListActivityDTO.setSortList(Sort.DISCOVER);
                break;
            case KEYWORDS:
                mDiscoverFilterDTO.setWithKeywords(mListActivityDTO.getId());
                mListActivityDTO.setSortList(Sort.DISCOVER);
                break;
        }

    }

}
