package br.com.tiagohs.popmovies.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.Movie;
import br.com.tiagohs.popmovies.util.MovieUtils;
import butterknife.BindView;

public class MovieDetailsOverviewFragment extends BaseFragment {
    private static final String TAG = MovieDetailsOverviewFragment.class.getSimpleName();

    private static final String ARG_MOVIE = "movie";

    @BindView(R.id.title_original_movie)
    TextView mNomeOriginalMovie;

    @BindView(R.id.idioma_movie)
    TextView mIdiomaMovie;

    @BindView(R.id.data_lancamento_movie)
    TextView mDataLancamentoMovie;

    @BindView(R.id.sinopse_movie)
    TextView mSinopseMovie;

    @BindView(R.id.adult_movie)
    TextView mAdultMovie;

    private Movie mMovie;

    public static MovieDetailsOverviewFragment newInstance(Movie movie) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MOVIE, movie);

        MovieDetailsOverviewFragment movieDetailsFragment = new MovieDetailsOverviewFragment();
        movieDetailsFragment.setArguments(bundle);

        return movieDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovie = (Movie) getArguments().getSerializable(ARG_MOVIE);
        Log.i(TAG, "ID do Filme: " + mMovie.getId());

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdultMovie.setVisibility(View.INVISIBLE);
        updateUI();
    }

    private void updateUI() {

        mNomeOriginalMovie.setText(mMovie.getOriginal_title());

        mIdiomaMovie.setText(MovieUtils.formatIdioma(getActivity(), mMovie.getOriginal_language()));
        mSinopseMovie.setText(mMovie.getOverview());
        mDataLancamentoMovie.setText(mMovie.getRelease_date());

        if (mMovie.isAdult()) {
            mAdultMovie.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_movie_detail_overview;
    }
}
