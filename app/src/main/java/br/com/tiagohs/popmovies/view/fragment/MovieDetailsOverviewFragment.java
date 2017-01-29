package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.data.repository.MovieRepositoryImpl;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.movie.ReleaseDate;
import br.com.tiagohs.popmovies.model.movie.ReleaseInfo;
import br.com.tiagohs.popmovies.model.response.RankingResponse;
import br.com.tiagohs.popmovies.presenter.MovieDetailsOverviewPresenter;
import br.com.tiagohs.popmovies.util.AnimationsUtils;
import br.com.tiagohs.popmovies.util.DTOUtils;
import br.com.tiagohs.popmovies.util.LocaleUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ItemType;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.ReleaseType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.MoviesDetailsOverviewView;
import br.com.tiagohs.popmovies.view.activity.ListsDefaultActivity;
import br.com.tiagohs.popmovies.view.activity.MovieDetailActivity;
import br.com.tiagohs.popmovies.view.activity.WebViewActivity;
import br.com.tiagohs.popmovies.view.adapters.ListMoviesAdapter;
import br.com.tiagohs.popmovies.view.adapters.ListWordsAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.ListWordsCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.MovieDontWantSeeCallback;
import br.com.tiagohs.popmovies.view.callbacks.MovieFavoriteCallback;
import br.com.tiagohs.popmovies.view.callbacks.MovieWantSeeCallback;
import br.com.tiagohs.popmovies.view.callbacks.MovieWatchedCallback;
import butterknife.BindView;
import butterknife.OnClick;

public class MovieDetailsOverviewFragment extends BaseFragment implements MoviesDetailsOverviewView, MovieWatchedCallback {
    private static final String TAG = MovieDetailsOverviewFragment.class.getSimpleName();

    private static final String ARG_MOVIE = "movie";

    @BindView(R.id.sinopse_movie)                           TextView mSinopseMovie;
    @BindView(R.id.adult_movie)                             TextView mAdultMovie;
    @BindView(R.id.generos_recycler_view)                   RecyclerView mGenerosRecyclerView;
    @BindView(R.id.imdb_raking_progress)                    ProgressBar mImdbRankingProgress;
    @BindView(R.id.imdb_raking)                             TextView mImdbRanking;
    @BindView(R.id.imdb_num_votos)                          TextView mImdbVotes;
    @BindView(R.id.tomatoes_ranking)                        TextView mTomatoesRanking;
    @BindView(R.id.tomatoes_ranking_progress)               ProgressBar mTomatoesRankingProgress;
    @BindView(R.id.tomatoes_num_votos)                      TextView mTomatoesVotes;
    @BindView(R.id.metascore_ranking)                       TextView mMetascoreRanking;
    @BindView(R.id.metascore_ranking_progress)              ProgressBar mMetascoreRankingProgress;
    @BindView(R.id.aspas)                                   TextView mAspas;
    @BindView(R.id.movie_tomatoes_consensus)                TextView mTomatoesConsensus;
    @BindView(R.id.movie_nomeacoes)                         TextView mMovieNomeacoes;
    @BindView(R.id.imdb_riple)                              MaterialRippleLayout mImdbRiple;
    @BindView(R.id.tomatoes_riple)                          MaterialRippleLayout mTomatoesRiple;
    @BindView(R.id.metascore_riple)                         MaterialRippleLayout mMetascoreRiple;
    @BindView(R.id.movie_details_titulo_original)           TextView mTituloOriginal;
    @BindView(R.id.movie_details_idioma_original)           TextView mIdiomaOriginal;
    @BindView(R.id.movie_details_orcamento_original)        TextView mOcamento;
    @BindView(R.id.movie_details_receita_original)          TextView mReceita;
    @BindView(R.id.keywords_recycler_view)                  RecyclerView mKeywordsRecyclerView;
    @BindView(R.id.label_movie_details_idioma_original)     TextView mLabelIdiomaOriginal;
    @BindView(R.id.label_movie_details_titulo_original)     TextView mLabelTituloOriginal;
    @BindView(R.id.label_movie_details_receita_original)    TextView mLabelOcamento;
    @BindView(R.id.label_movie_details_orcamento_original)  TextView mLabelReceita;
    @BindView(R.id.label_movie_nomeacoes)                   TextView mLabelMovieNomeacoes;
    @BindView(R.id.tomatoes_consensus_assign)               TextView mTomatoesConsensusAssign;
    @BindView(R.id.imdb_raking_container)                   FrameLayout mImdbRakingContainer;
    @BindView(R.id.tomatoes_ranking_container)              FrameLayout mTomatoesRankingContainer;
    @BindView(R.id.metascore_ranking_container)             FrameLayout mMetascoreRankingContainer;
    @BindView(R.id.tomatoes_consensus_container)            RelativeLayout mTomatoesConsensusContainer;
    @BindView(R.id.rankings_progress)                       ProgressWheel mRankingProgress;
    @BindView(R.id.rankings_container)                      RelativeLayout mRankingContainer;
    @BindView(R.id.container_similares)                     LinearLayout mSimilaresTitleContainer;
    @BindView(R.id.btn_favorito)                            ImageButton mFavoritoButton;
    @BindView(R.id.btn_quero_ver)                           Button mWantSeeButton;
    @BindView(R.id.btn_nao_quero_ver)                       Button mDontWantSeeButton;
    @BindView(R.id.riple_pagina_inicial)                    MaterialRippleLayout mPaginaInicialContainer;
    @BindView(R.id.riple_imdb)                              MaterialRippleLayout mImdbContainer;
    @BindView(R.id.imdb_reviews_riple)                      MaterialRippleLayout mImdbReviewContainer;
    @BindView(R.id.tomatoes_reviews_riple)                  MaterialRippleLayout mTomatoesReviewContainer;
    @BindView(R.id.elenco_nao_encontrado)                   TextView mElencoNaoEncontrado;
    @BindView(R.id.equipe_tecnica_nao_encontrada)           TextView mEquipeNaoEncontrada;
    @BindView(R.id.label_movie_details_date_mundial)        TextView mReleaseDateMundial;
    @BindView(R.id.label_movie_details_date_pais_atual)     TextView mReleaseDatePaisAtual;

    @Inject
    MovieDetailsOverviewPresenter mPresenter;

    private MovieDetails mMovie;
    private RankingResponse mMovieRankings;

    private ListWordsAdapter mGenerosAdapter;

    private ListWordsCallbacks mGenresCallbacks;
    private ListWordsCallbacks mKeyWordsCallbacks;

    private boolean mIsFavorito;
    private boolean mIsWantSeePressed;
    private boolean mIsDontWantSeePressed;
    private MovieWantSeeCallback mMovieWantSeeCallback;
    private MovieFavoriteCallback mMovieFavoriteCallback;
    private MovieDontWantSeeCallback mMovieDontWantSeeCallback;

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

        mGenresCallbacks = (ListWordsCallbacks) context;
        mKeyWordsCallbacks = (ListWordsCallbacks) context;
        mMovieWantSeeCallback = (MovieWantSeeCallback) context;
        mMovieFavoriteCallback = (MovieFavoriteCallback) context;
        mMovieDontWantSeeCallback = (MovieDontWantSeeCallback) context;

        ((MovieDetailActivity) getActivity()).setCallback(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mGenresCallbacks = null;
        mKeyWordsCallbacks = null;
        mMovieWantSeeCallback = null;
        mMovieFavoriteCallback = null;
        mMovieDontWantSeeCallback = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        mPresenter.setMovieRepository(new MovieRepositoryImpl(getContext()));
        mPresenter.setProfileID(PrefsUtils.getCurrentProfile(getContext()).getProfileID());
        mPresenter.setView(this);

        mMovie = (MovieDetails) getArguments().getSerializable(ARG_MOVIE);
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void init() {
        if (mMovie.getSimilarMovies().isEmpty())
            setSimilaresVisibility(View.GONE);

        if (ViewUtils.isEmptyValue(mMovie.getImdbID())) {
            setImdbRakingContainerVisibility(View.GONE);
            setTomatoesRakingContainerVisibility(View.GONE);
            setMetascoreRakingContainerVisibility(View.GONE);
            setTomatoesConsensusContainerVisibility(View.GONE);
            setTomatoesRakingContainerVisibility(View.GONE);
            setImdbRakingContainerVisibility(View.GONE);
            setImdbVisibility(View.GONE);
            setImdbReviewsVisibility(View.GONE);
            setRankingContainerVisibility(View.GONE);
            setRankingProgressVisibility(View.GONE);
            setTomatoesReviewsVisibility(View.GONE);
            mMovieNomeacoes.setText(getString(R.string.nao_disponivel));
        } else
            mPresenter.getMoviesRankings(mMovie.getImdbID(), TAG);

        if (mMovie.isFavorite()) {
            updateFavoriteButton(R.drawable.ic_favorite_clicked);
            mIsFavorito = true;
        }

        if (mMovie.getStatusDB() == MovieDB.STATUS_WANT_SEE) {
            updateWantSeeState(R.drawable.ic_quero_ver_white, R.drawable.background_want_see, android.R.color.white);
            mIsWantSeePressed = true;
        }

        if (mMovie.getStatusDB() == MovieDB.STATUS_DONT_WANT_SEE) {
            updateDontWantSeeState(R.drawable.ic_nao_quero_ver_white, R.drawable.background_dont_want_see, android.R.color.white);
            mIsDontWantSeePressed = true;
        }

        setPaginaInicialVisibility(ViewUtils.isEmptyValue(mMovie.getHomepage()) ? View.GONE : View.VISIBLE);

        configuraRecyclersViews();
        configurarSimilares();
        updateUI();

        if (mMovie.getCast().isEmpty()) {
            mElencoNaoEncontrado.setVisibility(View.VISIBLE);
            mElencoNaoEncontrado.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
        } else
           addFragment(R.id.container_elenco, ListPersonsDefaultFragment.newInstance(DTOUtils.createCastPersonListDTO(mMovie.getCast()), ListPersonsDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false)));

        if (mMovie.getCrew().isEmpty()) {
            mEquipeNaoEncontrada.setVisibility(View.VISIBLE);
            mEquipeNaoEncontrada.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
        } else
            addFragment(R.id.container_equipe_tecnica, ListPersonsDefaultFragment.newInstance(DTOUtils.createCrewPersonListDTO(mMovie.getCrew()), ListPersonsDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false)));
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

    public void setMovie(MovieDetails movie) {
        mMovie = movie;
    }

    public void setMovieRankings(RankingResponse movieRankings) {
        mMovieRankings = movieRankings;
    }

    private void configuraRecyclersViews() {
        mGenerosRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
    }

    public void configurarSimilares() {
        addFragment(R.id.fragment_similares, ListMoviesDefaultFragment.newInstance(mMovie.getId(), Sort.SIMILARS, R.layout.item_similares_movie, R.layout.fragment_list_movies_default_no_pull, new HashMap<String, String>(), ListMoviesDefaultFragment.createLinearListArguments(RecyclerView.HORIZONTAL, false)));
    }

    public void updateIMDB(String ranking, String votes) {
        ViewUtils.createRatingGadget(getActivity(), Float.parseFloat(ranking), mImdbRankingProgress, 10);
        mImdbRanking.setText(String.format("%.1f", Float.parseFloat(ranking)));
        mImdbVotes.setText(getString(R.string.imdb_votes, votes));
    }

    public void updateTomatoes(String ranking, String votes) {
        ViewUtils.createRatingGadget(getActivity(), Float.parseFloat(ranking), mTomatoesRankingProgress, 10);
        mTomatoesRanking.setText(String.format("%.1f", Float.parseFloat(ranking)));
        mTomatoesVotes.setText(getString(R.string.tomatoes_num_reviews, votes));
    }

    public void updateMetascore(String ranking) {
        ViewUtils.createRatingGadget(getActivity(), Float.parseFloat(ranking), mMetascoreRankingProgress, 100);
        mMetascoreRanking.setText(ranking);
    }

    public void updateTomatoesConsensus(String tomatoConsensus) {
        mAspas.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "TimesNewRoman.ttf"));
        mTomatoesConsensus.setText(tomatoConsensus);
        mTomatoesConsensus.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
    }

    public void updateNomeacoes(String response) {
        mMovieNomeacoes.setText(ViewUtils.isEmptyValue(response) ? getString(R.string.nao_disponivel) : response);
    }

    private void updateUI() {

        mMovieNomeacoes.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
        mLabelMovieNomeacoes.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));

        mSinopseMovie.setText(ViewUtils.isEmptyValue(mMovie.getOverview()) ? getResources().getString(R.string.nao_ha_sinopse, LocaleUtils.getLocaleLanguageName()) : mMovie.getOverview());
        mSinopseMovie.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));

        mAdultMovie.setVisibility(mMovie.isAdult() ? View.VISIBLE : View.GONE);
        mAdultMovie.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));

        mTituloOriginal.setText(mMovie.getOriginalTitle());
        mTituloOriginal.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
        mLabelTituloOriginal.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));

        mIdiomaOriginal.setText(ViewUtils.isEmptyValue(mMovie.getOriginalLanguage()) ? getString(R.string.nao_disponivel) : MovieUtils.formatIdioma(getActivity(), mMovie.getOriginalLanguage()));
        mIdiomaOriginal.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
        mLabelIdiomaOriginal.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));

        mOcamento.setText(mMovie.getBudget() != 0 ? MovieUtils.formatCurrency(mMovie.getBudget()) : getString(R.string.nao_disponivel));
        mOcamento.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
        mLabelOcamento.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));

        mReceita.setText(mMovie.getRevenue() != 0 ? MovieUtils.formatCurrency(mMovie.getRevenue()) : getString(R.string.nao_disponivel));
        mReceita.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
        mLabelReceita.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));

        mReleaseDateMundial.setText(ViewUtils.isEmptyValue(mMovie.getReleaseDate()) ? getString(R.string.nao_disponivel) : getString(R.string.movie_data_lancamento_mundial, MovieUtils.formateDate(mMovie.getReleaseDate())));
        mReleaseDateMundial.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));

        mReleaseDatePaisAtual.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));

        ReleaseInfo releaseTemp = new ReleaseInfo(LocaleUtils.getLocaleCountryISO().toUpperCase());
        if (mMovie.getReleases().contains(releaseTemp)) {
            List<ReleaseDate> releaseDate = mMovie.getReleases().get(mMovie.getReleases().indexOf(releaseTemp)).getReleaseDates();

            for (ReleaseDate r : releaseDate) {
                if (r.getType().equals(ReleaseType.THEATRICAL)) {
                    mReleaseDatePaisAtual.setText(getString(R.string.movie_data_lancamento_pais_origem, LocaleUtils.getLocaleCountryName(), MovieUtils.formateDate(r.getReleaseDate())));
                    break;
                }
            }

        } else
            mReleaseDatePaisAtual.setText(getString(R.string.movie_data_lancamento_pais_origem, LocaleUtils.getLocaleCountryName(), getString(R.string.nao_disponivel)));

        if (!mMovie.getKeywords().isEmpty()) {
            mKeywordsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
            mKeywordsRecyclerView.setAdapter(new ListWordsAdapter(getActivity(), DTOUtils.createKeywordsItemsListDTO(mMovie.getKeywords()), mKeyWordsCallbacks, ItemType.KEYWORD, R.layout.item_list_words_default));
            mKeywordsRecyclerView.setNestedScrollingEnabled(false);
        } else
            mKeywordsRecyclerView.setVisibility(View.GONE);

        if (!mMovie.getGenres().isEmpty()) {
            mGenerosAdapter = new ListWordsAdapter(getActivity(), DTOUtils.createGenresItemsListDTO(mMovie.getGenres()), mGenresCallbacks, ItemType.GENRE, R.layout.item_list_words_default);
            mGenerosRecyclerView.setAdapter(mGenerosAdapter);
            mGenerosRecyclerView.setNestedScrollingEnabled(false);
        } else
            mGenerosRecyclerView.setVisibility(View.GONE);

    }

    @OnClick(R.id.btn_favorito)
    public void onClickFavorito() {
        mIsFavorito = !mIsFavorito;

        mMovie.setFavorite(mIsFavorito);

        if (mIsFavorito) {
            updateFavoriteButton(R.drawable.ic_favorite_clicked);
            mMovieFavoriteCallback.onFavoritePressed();

            if (mIsWantSeePressed) {
                mIsWantSeePressed = false;
                updateWantSeeState(R.drawable.ic_quero_ver, android.R.color.transparent, R.color.secondary_text);
            }

            if (mIsDontWantSeePressed) {
                mIsDontWantSeePressed = false;
                updateDontWantSeeState(R.drawable.ic_nao_quero_ver, android.R.color.transparent, R.color.secondary_text);
            }

            Toast.makeText(getContext(), "Marcado como Favorito.", Toast.LENGTH_SHORT).show();
        } else {
            updateFavoriteButton(R.drawable.ic_favorite_border);
            Toast.makeText(getContext(), "Desmarcado como Favorito.", Toast.LENGTH_SHORT).show();
        }

        mPresenter.setMovieFavorite(mMovie);
    }

    @OnClick(R.id.btn_quero_ver)
    public void onClickWantSee() {
        mIsWantSeePressed = !mIsWantSeePressed;

        mPresenter.onClickWantSee(mMovie, mIsWantSeePressed);

        if (mIsWantSeePressed) {
            updateWantSeeState(R.drawable.ic_quero_ver_white, R.drawable.background_want_see, android.R.color.white);
            mMovieWantSeeCallback.onWantSeePressed();

            if (mIsFavorito) {
                mIsFavorito = false;
                updateFavoriteButton(R.drawable.ic_favorite_border);
            }

            if (mIsDontWantSeePressed) {
                mIsDontWantSeePressed = false;
                updateDontWantSeeState(R.drawable.ic_nao_quero_ver, android.R.color.transparent, R.color.secondary_text);
            }

            ViewUtils.createToastMessage(getContext(), "Marcado como 'Quero ver'");
        } else {
            updateWantSeeState(R.drawable.ic_quero_ver, android.R.color.transparent, R.color.secondary_text);
            ViewUtils.createToastMessage(getContext(), "Desmarcado como 'Quero ver'");
        }

    }

    @OnClick(R.id.btn_nao_quero_ver)
    public void onClickDontWantSee() {
        mIsDontWantSeePressed = !mIsDontWantSeePressed;

        mPresenter.onClickDontWantSee(mMovie, mIsDontWantSeePressed);

        if (mIsDontWantSeePressed) {
            updateDontWantSeeState(R.drawable.ic_nao_quero_ver_white, R.drawable.background_dont_want_see, android.R.color.white);
            mMovieDontWantSeeCallback.onDontWantSeePressed();

            if (mIsFavorito) {
                mIsFavorito = false;
                updateFavoriteButton(R.drawable.ic_favorite_border);
            }

            if (mIsWantSeePressed) {
                mIsWantSeePressed = false;
                updateWantSeeState(R.drawable.ic_quero_ver, android.R.color.transparent, R.color.secondary_text);
            }

            ViewUtils.createToastMessage(getContext(), "Marcado como 'Não quero ver'");
        } else {
            updateDontWantSeeState(R.drawable.ic_nao_quero_ver, android.R.color.transparent, R.color.secondary_text);
            ViewUtils.createToastMessage(getContext(), "Desmarcado como 'Não quero ver'");
        }

    }


    @Override
    public void onWatchedPressed() {
        if (mIsWantSeePressed) {
            mIsWantSeePressed = false;
            updateWantSeeState(R.drawable.ic_quero_ver, android.R.color.transparent, R.color.secondary_text);
        }

        if (mIsDontWantSeePressed) {
            mIsDontWantSeePressed = false;
            updateDontWantSeeState(R.drawable.ic_nao_quero_ver, android.R.color.transparent, R.color.secondary_text);
        }

    }

    private void updateWantSeeState(int iconID, int backgroundColor, int textColor) {
        mWantSeeButton.setTextColor(ViewUtils.getColorFromResource(getContext(), textColor));
        mWantSeeButton.setCompoundDrawablesWithIntrinsicBounds(iconID, 0, 0, 0);
        mWantSeeButton.setBackground(ViewUtils.getDrawableFromResource(getContext(), backgroundColor));
    }

    private void updateDontWantSeeState(int iconID, int backgroundColor, int textColor) {
        mDontWantSeeButton.setTextColor(ViewUtils.getColorFromResource(getContext(), textColor));
        mDontWantSeeButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconID, 0);
        mDontWantSeeButton.setBackground(ViewUtils.getDrawableFromResource(getContext(), backgroundColor));
    }

    private void updateFavoriteButton(int drawable) {
        mFavoritoButton.setImageDrawable(ViewUtils.getDrawableFromResource(getContext(), drawable));
    }

    @OnClick(R.id.tomatoes_riple)
    public void onTomatoesClick() {
        if (mMovieRankings.getTomatoURL() != null)
            startActivityForResult(WebViewActivity.newIntent(getActivity(), mMovieRankings.getTomatoURL(), mMovie.getTitle()), 0);
    }

    @OnClick(R.id.imdb_riple)
    public void onIMDBClick() {
        if (mMovieRankings.getImdbID() != null)
            startActivityForResult(WebViewActivity.newIntent(getActivity(), getString(R.string.imdb_link, mMovie.getImdbID()), mMovie.getTitle()), 0);
    }

    @OnClick(R.id.metascore_riple)
    public void onMetascoreClick() {
        if (mMovieRankings.getMetascoreRating() != null)
            startActivityForResult(WebViewActivity.newIntent(getActivity(), getString(R.string.metacritic_link, mMovie.getOriginalTitle()), mMovie.getTitle()), 0);
    }

    @OnClick(R.id.elenco_riple)
    public void onClickElencoTitle() {
        if (!mMovie.getCast().isEmpty())
            startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(getString(R.string.elenco_title_activity), mMovie.getTitle(), R.layout.item_list_movies, ListType.PERSON), DTOUtils.createCastPersonListDTO(mMovie.getCast())));
    }

    @OnClick(R.id.equipe_tecnica_riple)
    public void onClickEquipeTitle() {
        if (!mMovie.getCrew().isEmpty())
            startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(getString(R.string.equipe_tecnica_title), mMovie.getTitle(), R.layout.item_list_movies, ListType.PERSON), DTOUtils.createCrewPersonListDTO(mMovie.getCrew())));
    }

    @OnClick(R.id.similares_riple)
    public void onClickSimilaresTitle() {
        if (!mMovie.getSimilarMovies().isEmpty())
            startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(mMovie.getId(), getString(R.string.similares_title_activity), mMovie.getTitle(), Sort.SIMILARS, R.layout.item_list_movies, ListType.MOVIES)));
    }

    @OnClick(R.id.btn_pagina_inicial)
    public void onPaginaInicialClick() {
        if (mMovie.getHomepage() != null)
            startActivityForResult(WebViewActivity.newIntent(getActivity(), mMovie.getHomepage(), mMovie.getTitle()), 0);
    }

    @OnClick(R.id.btn_imdb)
    public void onImdbClick() {
        if (mMovie.getImdbID() != null)
            startActivityForResult(WebViewActivity.newIntent(getActivity(), getString(R.string.movie_imdb, mMovie.getImdbID()), mMovie.getTitle()), 0);
    }

    @OnClick(R.id.btn_wiki)
    public void onWikiClick() {
        startActivityForResult(WebViewActivity.newIntent(getActivity(), getString(R.string.person_wiki, mMovie.getOriginalTitle()), mMovie.getTitle()), 0);
    }

    public void setImdbRakingContainerVisibility(int visibilityState) {
        mImdbRakingContainer.setVisibility(visibilityState);
        mImdbRiple.setVisibility(visibilityState);
    }

    public void setTomatoesRakingContainerVisibility(int visibilityState) {
        mTomatoesRankingContainer.setVisibility(visibilityState);
        mTomatoesRiple.setVisibility(visibilityState);
    }

    public void setMetascoreRakingContainerVisibility(int visibilityState) {
        mMetascoreRankingContainer.setVisibility(visibilityState);
        mMetascoreRiple.setVisibility(visibilityState);
    }

    public void setTomatoesConsensusContainerVisibility(int visibilityState) {
        mTomatoesConsensusContainer.setVisibility(visibilityState);
        mTomatoesConsensusAssign.setVisibility(visibilityState);
    }

    public void setSimilaresVisibility(int visibilityState) {
        mSimilaresTitleContainer.setVisibility(visibilityState);
    }

    public void setPaginaInicialVisibility(int visibilityState) {
        mPaginaInicialContainer.setVisibility(visibilityState);
    }

    public void setImdbVisibility(int visibilityState) {
        mImdbContainer.setVisibility(visibilityState);
    }

    @OnClick(R.id.imdb_reviews_riple)
    public void onClickIMDBReviews() {
        if (mMovie.getImdbID() != null)
            startActivityForResult(WebViewActivity.newIntent(getActivity(), getString(R.string.imdb_reviews_link, mMovie.getImdbID()), mMovie.getTitle()), 0);
    }

    @OnClick(R.id.tomatoes_reviews_riple)
    public void onClickTomatoesReviews() {
        if (mMovieRankings.getTomatoURL() != null)
            startActivityForResult(WebViewActivity.newIntent(getActivity(), getString(R.string.tomatoes_reviews_link, mMovieRankings.getTomatoURL()), mMovie.getTitle()), 0);
    }

    public void setImdbReviewsVisibility(int visibility) {
        mImdbReviewContainer.setVisibility(visibility);
    }

    public void setTomatoesReviewsVisibility(int visibility) {
        mTomatoesReviewContainer.setVisibility(visibility);
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_movie_detail_overview;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
                mSnackbar.dismiss();
            }
        };
    }

    public void setRankingProgressVisibility(int visibility) {
        mRankingProgress.setVisibility(visibility);
    }

    public void setRankingContainerVisibility(int visibility) {
        mRankingContainer.setVisibility(visibility);
    }

    @Override
    public void setProgressVisibility(int visibityState) {

    }

}
