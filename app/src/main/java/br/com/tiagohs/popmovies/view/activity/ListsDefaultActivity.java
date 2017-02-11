package br.com.tiagohs.popmovies.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.FilterValuesDTO;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.dto.PersonListDTO;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Param;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.callbacks.FiltersMoviesCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.PersonCallbacks;
import br.com.tiagohs.popmovies.view.fragment.FilterDialogFragment;
import br.com.tiagohs.popmovies.view.fragment.ListMoviesDefaultFragment;
import br.com.tiagohs.popmovies.view.fragment.ListPersonsDefaultFragment;

public class ListsDefaultActivity extends BaseActivity implements ListMoviesCallbacks, PersonCallbacks, FiltersMoviesCallbacks {
    private static final String TAG = ListsDefaultActivity.class.getSimpleName();

    private static final String ARG_FTO = "br.com.tiagohs.popmovies.dto";
    private static final String ARG_PARAMETERS = "br.com.tiagohs.popmovies.parameters";
    private static final String ARG_PERSON_LIST = "br.com.tiagohs.popmovies.person_list";
    private static final String ARG_MOVIES_LIST = "br.com.tiagohs.popmovies.movies_list";

    public static final int LINEAR_LAYOUT = 0;
    public static final int GRID_LAYOUT = 1;
    public static final int STAGGERED = 2;

    private Map<String, String> mParameters;
    private Map<String, String> mParametersFilter;

    private List<PersonListDTO> mPersons;
    private List<MovieListDTO> mMovies;

    private Sort mOriginalSort;
    private ListActivityDTO mListActivityDTO;

    public static Intent newIntent(Context context, ListActivityDTO listDTO) {
        Intent intent = new Intent(context, ListsDefaultActivity.class);
        intent.putExtra(ARG_FTO, listDTO);
        return intent;
    }

    public static Intent newIntent(Context context, ListActivityDTO listDTO, Map<String, String> parameters) {
        Intent intent = new Intent(context, ListsDefaultActivity.class);
        intent.putExtra(ARG_FTO, listDTO);
        intent.putExtra(ARG_PARAMETERS, (HashMap<String, String>) parameters);

        return intent;
    }

    public static Intent newIntent(Context context, ListActivityDTO listDTO, List<PersonListDTO> persons) {
        Intent intent = new Intent(context, ListsDefaultActivity.class);
        intent.putExtra(ARG_FTO, listDTO);
        intent.putExtra(ARG_PERSON_LIST, (ArrayList<PersonListDTO>) persons);

        return intent;
    }

    public static Intent newIntent(Context context, List<MovieListDTO> movies, ListActivityDTO listDTO) {
        Intent intent = new Intent(context, ListsDefaultActivity.class);
        intent.putExtra(ARG_FTO, listDTO);
        intent.putExtra(ARG_MOVIES_LIST, (ArrayList<MovieListDTO>) movies);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListActivityDTO = (ListActivityDTO) getIntent().getSerializableExtra(ARG_FTO);
        mParameters = (HashMap<String, String>) getIntent().getSerializableExtra(ARG_PARAMETERS);
        mPersons = (ArrayList<PersonListDTO>) getIntent().getSerializableExtra(ARG_PERSON_LIST);
        mMovies =  (ArrayList<MovieListDTO>) getIntent().getSerializableExtra(ARG_MOVIES_LIST);

        setActivityTitle(mListActivityDTO.getNameActivity());
        setActivitySubtitle(mListActivityDTO.getSubtitleActivity());

        onUpdateUI(mParameters);
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_default;
    }

    @Override
    protected int getMenuLayoutID() {

        if (null != mListActivityDTO.getSortList()) {
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
                onUpdateUI(null == mParametersFilter ? mParameters : mParametersFilter);
                mSnackbar.dismiss();
            }
        };
    }

    private void onUpdateUI(Map<String, String> parametersCurrent) {
        Fragment fragment = null;

        switch (mListActivityDTO.getListType()) {
            case MOVIES:
                if (null == mMovies)
                    fragment = ListMoviesDefaultFragment.newInstance(mListActivityDTO.getId(), mListActivityDTO.getSortList(), mListActivityDTO.getLayoutID(), R.layout.fragment_list_movies_default, parametersCurrent, ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns)));
                else
                    fragment = ListMoviesDefaultFragment.newInstance(mListActivityDTO.getSortList(), mListActivityDTO.getLayoutID(), R.layout.fragment_list_movies_default, mMovies, ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns)));
                break;
            case PERSON:
                if (null == mPersons)
                    fragment = ListPersonsDefaultFragment.newInstance(mListActivityDTO.getSortList(), ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns)));
                else
                    fragment = ListPersonsDefaultFragment.newInstance(mPersons, ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns)));
                break;
        }

        startFragment(R.id.content_fragment, fragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_filter:
                FilterDialogFragment filter = FilterDialogFragment.newInstance(mListActivityDTO.getListType());
                filter.show(getSupportFragmentManager().beginTransaction(), getString(R.string.filter_title));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieSelected(int movieID, ImageView imageView) {
        ViewUtils.startMovieActivityWithTranslation(this, movieID, imageView, getString(R.string.poster_movie));
    }

    @Override
    public void onClickPerson(int personID) {
        startActivity(PersonDetailActivity.newIntent(this, personID));
    }

    @Override
    public void onFilterChanged(FilterValuesDTO filters) {
        if (mParametersFilter == null) {
            mParametersFilter = new HashMap<>();
            mParametersFilter.putAll(mParameters);
        }

        addFilterItemParameter(Param.SORT_BY.getParam(), filters.getSortBy());
        addFilterItemParameter(Param.INCLUDE_ADULT.getParam(), String.valueOf(filters.isIncludeAdult()));
        addFilterItemParameter(Param.PRIMARY_RELEASE_YEAR.getParam(), filters.getReleaseYear());
        addFilterItemParameter(Param.PRIMARY_RELEASE_DATE_GTE.getParam(), filters.getPrimaryRelaseDateGte());
        addFilterItemParameter(Param.PRIMARY_RELEASE_DATE_LTE.getParam(), filters.getPrimaryRelaseDateLte());
        addFilterItemParameter(Param.VOTE_AVERAGE_GTE.getParam(), filters.getVoteAverageGte());

        additionalSearchConfigurations();
        onUpdateUI(mParametersFilter);
    }

    @Override
    public void onFilterReset() {
        mParametersFilter = null;

        if (null != mOriginalSort) {
            switch (mOriginalSort) {
                case GENEROS:
                case KEYWORDS:
                    mListActivityDTO.setSortList(mOriginalSort);
            }
        }

        onUpdateUI(mParameters);
    }

    private void additionalSearchConfigurations() {
        mOriginalSort = mListActivityDTO.getSortList();

        switch (mListActivityDTO.getSortList()) {
            case GENEROS:
                addFilterItemParameter(Param.WITH_GENRES.getParam(), String.valueOf(mListActivityDTO.getId()));
                mListActivityDTO.setSortList(Sort.DISCOVER);
                break;
            case KEYWORDS:
                addFilterItemParameter(Param.WITH_KEYWORDS.getParam(), String.valueOf(mListActivityDTO.getId()));
                mListActivityDTO.setSortList(Sort.DISCOVER);
                break;
        }

    }

    private void addFilterItemParameter(String param, String value) {
        if (null != value) {
            mParametersFilter.put(param, value);
        } else {
            if (mParametersFilter.containsKey(param))
                mParametersFilter.remove(param);
        }
    }
}