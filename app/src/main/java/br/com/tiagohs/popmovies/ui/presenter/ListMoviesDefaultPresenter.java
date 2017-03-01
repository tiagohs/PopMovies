package br.com.tiagohs.popmovies.ui.presenter;

import android.view.View;

import br.com.tiagohs.popmovies.model.dto.DiscoverDTO;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.ui.contracts.ListMoviesDefaultContract;
import br.com.tiagohs.popmovies.util.DTOUtils;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ListMoviesDefaultPresenter extends BasePresenter<ListMoviesDefaultContract.ListMoviesDefaultView, ListMoviesDefaultContract.ListMoviesDefaultInterceptor> implements ListMoviesDefaultContract.ListMoviesDefaultPresenter {
    private static final String TAG = ListMoviesDefaultPresenter.class.getSimpleName();

    private int mCurrentPage;
    private int mTotalPages;

    private int mStatus;
    private boolean isSaved;
    private boolean isFavorite;
    private boolean dontIsFavorite;

    private int mId;
    private Sort mTypeList;
    private DiscoverDTO mDiscoverDTO;

    private int mListIndex = 0;
    private int mPosition;
    private long mProfileID;

    public ListMoviesDefaultPresenter(ListMoviesDefaultContract.ListMoviesDefaultInterceptor listMoviesDefaultInterceptor, CompositeDisposable subscribes) {
        super(listMoviesDefaultInterceptor, subscribes);

        mCurrentPage = 0;
    }

    public void setProfileID(long profileID) {
        this.mProfileID = profileID;
    }

    @Override
    public Observable<Movie> isJaAssistido(int movieID) {
        return mInterceptor.findMovieByServerID(movieID, mProfileID)
                            .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void getMovies(int id, Sort typeList, String tag, DiscoverDTO discoverDTO) {
        mView.setProgressVisibility(View.VISIBLE);

        mId = id;
        mTypeList = typeList;
        mDiscoverDTO = discoverDTO;

        if (mView.isInternetConnected()) {
            onCreateObservable(id, typeList, mDiscoverDTO)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onObserverMovies());

            mView.setRecyclerViewVisibility(isFirstPage() ? View.GONE : View.VISIBLE);
        } else {
            noConnectionError();
        }
    }

    private Observable<GenericListResponse<Movie>> onCreateObservable(int id, Sort typeList, DiscoverDTO discoverDTO) {
        Observable<GenericListResponse<Movie>> observable = null;

        switch (typeList) {
            case FAVORITE:
            case ASSISTIDOS:
            case QUERO_VER:
            case NAO_QUERO_VER:
                observable = getMoviesDB(typeList);
                break;
            case GENEROS:
                observable = mInterceptor.getMoviesByGenres(id, ++mCurrentPage);
                break;
            case DISCOVER:
                observable = mInterceptor.getDiscoverMovies(++mCurrentPage, discoverDTO);
                break;
            case KEYWORDS:
                observable = mInterceptor.getMoviesByKeywords(id, ++mCurrentPage);
                break;
            case SIMILARS:
                observable = mInterceptor.getMovieSimilars(id, ++mCurrentPage);
                break;
            case PERSON_MOVIES_CARRER:
            case PERSON_CONHECIDO_POR:
                observable = mInterceptor.getPersonMoviesCredits(id, ++mCurrentPage);
                break;

        }

        return observable;
    }

    private Observer<GenericListResponse<Movie>> onObserverMovies() {
        return new Observer<GenericListResponse<Movie>>() {
                           @Override
                           public void onSubscribe(Disposable d) {
                               mSubscribers.add(d);
                           }

                           @Override
                           public void onNext(GenericListResponse<Movie> response) {
                               onResponse(response);
                           }

                           @Override
                           public void onError(Throwable e) {
                               mView.onErrorInServer();
                           }

                           @Override
                           public void onComplete() {}
                       };

    }

    public void getMovieDetails(int movieID, boolean isSaved, boolean isFavorite,  boolean dontIsFavorite, int status, String tag, int position) {
        this.mStatus = status;
        this.isSaved = isSaved;
        this.isFavorite = isFavorite;
        this.mPosition = position;
        this.dontIsFavorite = dontIsFavorite;

        if (mView.isInternetConnected()) {
            mView.showDialogProgress();
            mInterceptor.getMovieDetails(movieID, new String[]{})
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribe(onMovieDetailsObserver());

        } else {
            noConnectionError();
        }
    }

    private Observer<MovieDetails> onMovieDetailsObserver() {
        return new Observer<MovieDetails>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribers.add(d);
            }

            @Override
            public void onNext(MovieDetails value) {
                onMovieDetailsReceived(value);
            }

            @Override
            public void onError(Throwable e) {
                mView.onErrorSaveMovie();
            }

            @Override
            public void onComplete() {}
        };
    }

    public void onMovieDetailsReceived(MovieDetails movie) {
        boolean isDelete = false;

        if (dontIsFavorite) { //Removendo um Favorito
            mInterceptor.saveMovie(movie, isFavorite, mStatus, mProfileID)
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribe(onSavedMovie());

            mView.onDeleteSaveSucess();
            isDelete = true;
        } else if (isSaved) { //Removendo um Filme Assistido
            mInterceptor.saveMovie(movie, isFavorite, mStatus, mProfileID)
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribe(onSavedMovie());

            mView.onSucessSaveMovie();
        } else if ((!isSaved) && isFavorite) { //Adicionando um filme favorito
            mInterceptor.saveMovie(movie, isFavorite, mStatus, mProfileID)
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribe(onSavedMovie());

            mView.onSucessSaveMovie();
        } else { //Senão, é pra deletar
            mSubscribers.add(mInterceptor.deleteMovie(movie, mProfileID)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe());

            mView.onDeleteSaveSucess();
            isDelete = true;
        }

        if (isDelete)
            mView.notifyMovieRemoved(mPosition);
        else {
            mListIndex = 0;
            getMovies(mId, mTypeList, TAG, mDiscoverDTO);
        }

        mView.hideDialogProgress();
    }

    private Observer<Long> onSavedMovie() {
        return new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribers.add(d);
            }

            @Override
            public void onNext(Long value) {
                if (value == -1)
                    mView.onErrorSaveMovie();
            }

            @Override
            public void onError(Throwable e) {
                mView.onErrorSaveMovie();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private Observable<GenericListResponse<Movie>> getMoviesDB(Sort typeList) {
        mView.setProgressVisibility(View.VISIBLE);
        mView.setRecyclerViewVisibility(mListIndex == 0 ? View.GONE : View.VISIBLE);

        return mInterceptor.getMovieDB(typeList, mProfileID, ++mCurrentPage)
                           .observeOn(AndroidSchedulers.mainThread());
    }

    public void onResponse(GenericListResponse<Movie> response) {
        mCurrentPage = response.getPage();
        mTotalPages = response.getTotalPage();

        if (mView.isAdded()) {
            if (isFirstPage()) {
                mView.setListMovies(DTOUtils.createMovieListDTO(response.getResults()), hasMorePages());
                mView.setupRecyclerView();
            } else {
                mView.addAllMovies(DTOUtils.createMovieListDTO(response.getResults()), hasMorePages());
                mView.updateAdapter();
            }

            mView.setRecyclerViewVisibility(View.VISIBLE);
            mView.setProgressVisibility(View.GONE);
        }

    }

    private boolean isFirstPage() {
        return mCurrentPage == 1;
    }

    private boolean hasMorePages() {
        return mCurrentPage < mTotalPages;
    }
}
