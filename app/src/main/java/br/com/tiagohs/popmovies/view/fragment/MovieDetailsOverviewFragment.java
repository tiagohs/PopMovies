package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ItemListDTO;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.movie.ReleaseInfo;
import br.com.tiagohs.popmovies.util.LocaleUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.enumerations.ItemType;
import br.com.tiagohs.popmovies.view.adapters.ListMoviesAdapter;
import br.com.tiagohs.popmovies.view.adapters.ListWordsAdapter;
import br.com.tiagohs.popmovies.view.adapters.NotasAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.ListWordsCallbacks;
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

    private ListWordsAdapter mGenerosAdapter;

    private ListMoviesCallbacks mListMoviesCallbacks;
    private ListWordsCallbacks mGenresCallbacks;

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
        mListMoviesCallbacks = (ListMoviesCallbacks) context;
        mGenresCallbacks = (ListWordsCallbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListMoviesCallbacks = null;
        mGenresCallbacks = null;
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

        configuraRecyclersViews();
        updateUI();
    }

    private void configuraRecyclersViews() {
        List<MovieListDTO> movieListDTO = new ArrayList<>();

        for (MovieDetails movie : mMovie.getSimilarMovies())
            movieListDTO.add(new MovieListDTO(movie.getId(), movie.getPosterPath(), movie.getVoteAverage()));

        mGenerosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));

        mSimilaresRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        mSimilaresRecyclerView.setAdapter(new ListMoviesAdapter(getActivity(), movieListDTO, mListMoviesCallbacks, R.layout.item_similares_movie));
    }

    private void updateUI() {

        mDataLancamentoMovie.setText(mMovie.getReleaseDate() != null ? MovieUtils.formateDate(getActivity(), mMovie.getReleaseDate()) : "--");
        mDuracaoMovie.setText(mMovie.getRuntime() != 0 ? getResources().getString(R.string.movie_duracao, mMovie.getRuntime()) : "--");

        mSinopseMovie.setText(mMovie.getOverview() != null ? mMovie.getOverview() : getResources().getString(R.string.nao_ha_sinopse, LocaleUtils.getLocaleLanguageName()));
        mSinopseMovie.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));

        mClassificacaoMovie.setText(release());

        mAdultMovie.setVisibility(mMovie.isAdult() ? View.VISIBLE : View.GONE);
        mAdultMovie.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));

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
        mGenerosAdapter = new ListWordsAdapter(getActivity(), getItemsGenres(mMovie.getGenres()), mGenresCallbacks, ItemType.GENRE);
        mGenerosRecyclerView.setAdapter(mGenerosAdapter);
    }

    private List<ItemListDTO> getItemsGenres(List<Genre> genres) {
        List<ItemListDTO> list = new ArrayList<>();

        for (Genre g : genres)
            list.add(new ItemListDTO(g.getId(), g.getName()));

        return list;
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_movie_detail_overview;
    }
}
