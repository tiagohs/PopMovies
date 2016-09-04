package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.movie.ReleaseInfo;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.view.adapters.GenerosAdapter;
import br.com.tiagohs.popmovies.view.adapters.NotasAdapter;
import br.com.tiagohs.popmovies.view.adapters.SimilaresMoviesAdapter;
import butterknife.BindView;

public class MovieDetailsOverviewFragment extends BaseFragment {
    private static final String TAG = MovieDetailsOverviewFragment.class.getSimpleName();

    private static final String ARG_MOVIE = "movie";

    @BindView(R.id.data_lancamento_movie)
    TextView mDataLancamentoMovie;

    @BindView(R.id.sinopse_movie)
    TextView mSinopseMovie;

    @BindView(R.id.adult_movie)
    TextView mAdultMovie;

    @BindView(R.id.classificacao_movie)
    Button mClassificacaoMovie;

    @BindView(R.id.data_duracao_movie)
    TextView mDuracaoMovie;

    @BindView(R.id.generos_recycler_view)
    RecyclerView mGenerosRecyclerView;

    @BindView(R.id.similares_recycler_view)
    RecyclerView mSimilaresRecyclerView;

    private MovieDetails mMovie;
    private NotasAdapter mNotasAdapter;
    private GenerosAdapter mGenerosAdapter;

    private Callbacks mCallbacks;

    public static MovieDetailsOverviewFragment newInstance(MovieDetails movie) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MOVIE, movie);

        MovieDetailsOverviewFragment movieDetailsFragment = new MovieDetailsOverviewFragment();
        movieDetailsFragment.setArguments(bundle);

        return movieDetailsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }


    public interface Callbacks {
        void onMovieSelected(int movieID, ImageView posterMovie);
        void onGenreSelected(Genre genre);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovie = (MovieDetails) getArguments().getSerializable(ARG_MOVIE);
        Log.i(TAG, "ID do Filme: " + mMovie.getId());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdultMovie.setVisibility(View.INVISIBLE);
        mGenerosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));

        mSimilaresRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        mSimilaresRecyclerView.setAdapter(new SimilaresMoviesAdapter(getActivity(), mMovie.getSimilarMovies(), mCallbacks));

        updateUI();
    }

    private void updateUI() {

        mDataLancamentoMovie.setText(MovieUtils.formateDate(getActivity(), mMovie.getReleaseDate()));
        mDuracaoMovie.setText(mMovie.getRuntime() + " Min.");
        mSinopseMovie.setText(mMovie.getOverview());
        mClassificacaoMovie.setText(release());
        mAdultMovie.setVisibility(mMovie.isAdult() ? View.VISIBLE : View.GONE);

        updateGeneros();
    }

    private String release() {

        for (ReleaseInfo releaseInfo : mMovie.getReleases()) {
            if (releaseInfo.getCountry().equals("US"))
                return releaseInfo.getReleaseDates().get(0).getCertification();
        }
        return "--";
    }

    private void updateGeneros() {
        mGenerosAdapter = new GenerosAdapter(getActivity(), mMovie.getGenres(), mCallbacks);
        mGenerosRecyclerView.setAdapter(mGenerosAdapter);
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_movie_detail_overview;
    }
}
