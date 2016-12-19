package br.com.tiagohs.popmovies.view.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
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
import br.com.tiagohs.popmovies.model.dto.PersonListDTO;
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
    private static final String ARG_FTO = "br.com.tiagohs.popmovies.dto";
    private static final String ARG_PARAMETERS = "br.com.tiagohs.popmovies.parameters";
    private static final String ARG_PERSON_LIST = "br.com.tiagohs.popmovies.person_list";

    private static final int CONTAINER_FRAGMENT_ID = R.id.content_fragment;

    public enum TypeListLayout { LINEAR_LAYOUT, GRID_LAYOUT, STAGGERED; }

    private ListActivityDTO mListActivityDTO;
    private Map<String, String> mParameters;
    private List<PersonListDTO> mPersons;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListActivityDTO = (ListActivityDTO) getIntent().getSerializableExtra(ARG_FTO);
        mParameters = (HashMap<String, String>) getIntent().getSerializableExtra(ARG_PARAMETERS);
        mPersons = (ArrayList<PersonListDTO>) getIntent().getSerializableExtra(ARG_PERSON_LIST);
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStart();
                mSnackbar.dismiss();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        mToolbar.setTitle(mListActivityDTO.getNameActivity());

        if (mListActivityDTO.getSubtitleActivity() != null)
            mToolbar.setSubtitle(mListActivityDTO.getSubtitleActivity());

        addFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mListActivityDTO.getListType().equals(ListType.MOVIES)) {
            getMenuInflater().inflate(R.menu.menu_list_defult, menu);
            return true;
        } else {
            getMenuInflater().inflate(R.menu.menu_principal, menu);
<<<<<<< HEAD
            return true;
=======
            return false;
>>>>>>> origin/master
        }
    }

    private void addFragment() {
        FragmentManager fm = getSupportFragmentManager();

        Fragment fragmentList = null;
        switch (mListActivityDTO.getListType()) {
            case MOVIES:
                fragmentList = ListMoviesDefaultFragment.newInstance(mListActivityDTO.getId(), mListActivityDTO.getSortList(), mListActivityDTO.getLayoutID(), mParameters, ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns)));
                break;
            case PERSON:
                if (mPersons == null)
                    fragmentList = ListPersonsDefaultFragment.newInstance(mListActivityDTO.getSortList(), ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns)));
                else
                    fragmentList = ListPersonsDefaultFragment.newInstance(mPersons, ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns)));
                break;
        }

        if (fm.findFragmentById(CONTAINER_FRAGMENT_ID) == null)
            initFragment(fm, fragmentList);
        else
            replaceFragment(fm, fragmentList);

    }

    private void initFragment(FragmentManager fm, Fragment fragment) {
        fm.beginTransaction()
                .add(CONTAINER_FRAGMENT_ID, fragment)
                .commit();
    }

    private void replaceFragment(FragmentManager fm, Fragment fragment) {
        fm.beginTransaction()
                .replace(CONTAINER_FRAGMENT_ID, fragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_filter:
                FilterDialogFragment filter = FilterDialogFragment.newInstance(mListActivityDTO.getListType());
                filter.show(getSupportFragmentManager().beginTransaction(), "Filtrar");
                return true;
            default:
                return false;
        }
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_list_movies_default;
    }

    @Override
    public void onMovieSelected(int movieID, ImageView imageView) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions transitionActivityOptions = ActivityOptions
                    .makeSceneTransitionAnimation(this, imageView, getString(R.string.poster_movie));
            startActivity(MovieDetailActivity.newIntent(this, movieID), transitionActivityOptions.toBundle());
        } else {
            startActivity(MovieDetailActivity.newIntent(this, movieID));
        }
    }

    @Override
    public void onClickPerson(int personID) {
        startActivity(PersonDetailActivity.newIntent(this, personID));
    }

    @Override
    public void onFilterChanged(FilterValuesDTO filters) {
        if (mParameters == null)
            mParameters = new HashMap<>();

        addItemParameter(Param.SORT_BY.getParam(), filters.getSortBy());
        addItemParameter(Param.INCLUDE_ADULT.getParam(), String.valueOf(filters.isIncludeAdult()));
        addItemParameter(Param.PRIMARY_RELEASE_YEAR.getParam(), filters.getReleaseYear());
        addItemParameter(Param.PRIMARY_RELEASE_DATE_GTE.getParam(), filters.getPrimaryRelaseDateGte());
        addItemParameter(Param.PRIMARY_RELEASE_DATE_LTE.getParam(), filters.getPrimaryRelaseDateLte());
        addItemParameter(Param.VOTE_AVERAGE_GTE.getParam(), filters.getVoteAverageGte());
        addItemParameter(Param.VOTE_AVERAGE_LTE.getParam(), filters.getVoteAverageLte());

        additionalSearchConfigurations();
        addFragment();
    }

    private void additionalSearchConfigurations() {

        switch (mListActivityDTO.getSortList()) {
            case GENEROS:
                addItemParameter(Param.WITH_GENRES.getParam(), String.valueOf(mListActivityDTO.getId()));
                mListActivityDTO.setSortList(Sort.DISCOVER);
                break;
            case KEYWORDS:
                addItemParameter(Param.WITH_KEYWORDS.getParam(), String.valueOf(mListActivityDTO.getId()));
                mListActivityDTO.setSortList(Sort.DISCOVER);
                break;
        }

    }

    private void addItemParameter(String param, String value) {
        if (value != null) {
            mParameters.put(param, value);
        } else {
            if (mParameters.containsKey(param))
                mParameters.remove(param);
        }
    }
}
