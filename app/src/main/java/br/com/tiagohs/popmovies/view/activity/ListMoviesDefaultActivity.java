package br.com.tiagohs.popmovies.view.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.view.fragment.ListMoviesDefaultFragment;

public class ListMoviesDefaultActivity extends BaseActivity implements ListMoviesCallbacks {
    private static final String ARG_FTO = "br.com.tiagohs.popmovies.dto";
    private static final String ARG_PARAMETERS = "br.com.tiagohs.popmovies.parameters";

    private ListActivityDTO mListActivityDTO;
    private Map<String, String> mParameters;

    public static Intent newIntent(Context context, ListActivityDTO listDTO) {
        Intent intent = new Intent(context, ListMoviesDefaultActivity.class);
        intent.putExtra(ARG_FTO, listDTO);
        return intent;
    }

    public static Intent newIntent(Context context, ListActivityDTO listDTO, Map<String, String> parameters) {
        Intent intent = new Intent(context, ListMoviesDefaultActivity.class);
        intent.putExtra(ARG_FTO, listDTO);
        intent.putExtra(ARG_PARAMETERS, (HashMap<String, String>) parameters);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mListActivityDTO = (ListActivityDTO) getIntent().getSerializableExtra(ARG_FTO);
        mParameters = (HashMap<String, String>) getIntent().getSerializableExtra(ARG_PARAMETERS);
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();

        mToolbar.setTitle(mListActivityDTO.getNameActivity());
        replaceFragment();
    }

    private void replaceFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.content_fragment);

        if (fragment == null) {
            fm.beginTransaction()
                    .add(R.id.content_fragment,
                         ListMoviesDefaultFragment.newInstance(mListActivityDTO.getId(), mListActivityDTO.getTypeList(), mListActivityDTO.getLayoutID(), mParameters, ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns))))
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
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
}
