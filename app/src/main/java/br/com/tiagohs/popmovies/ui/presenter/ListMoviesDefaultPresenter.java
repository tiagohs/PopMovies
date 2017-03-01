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

public class ListMoviesDefaultPresenter implements ListMoviesDefaultContract.ListMoviesDefaultPresenter {

    private static final String TAG = ListMoviesDefaultPresenter.class.getSimpleName();

    private ListMoviesDefaultContract.ListMoviesDefaultView mListMoviesDefaultView;

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

    private ListMoviesDefaultContract.ListMoviesDefaultInterceptor mInterceptor;

    private CompositeDisposable mSubscribes;

    public ListMoviesDefaultPresenter(ListMoviesDefaultContract.ListMoviesDefaultInterceptor listMoviesDefaultInterceptor, CompositeDisposable subscribes) {
        mCurrentPage = 0;
        mSubscribes = subscribes;

        mInterceptor = listMoviesDefaultInterceptor;
    }

    @Override
    public void onBindView(ListMoviesDefaultContract.ListMoviesDefaultView view) {
        mListMoviesDefaultView = view;

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
        mListMoviesDefaultView.setProgressVisibility(View.VISIBLE);

        mId = id;
        mTypeList = typeList;
        mDiscoverDTO = discoverDTO;

        if (mListMoviesDefaultView.isInternetConnected()) {
            onCreateObservable(id, typeList, mDiscoverDTO)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onObserverMovies());

            mListMoviesDefaultView.setRecyclerViewVisibility(isFirstPage() ? View.GONE : View.VISIBLE);
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
                               mSubscribes.add(d);
                           }

                           @Override
                           public void onNext(GenericListResponse<Movie> response) {
                               onResponse(response);
                           }

                           @Override
                           public void onError(Throwable e) {
                               e.printStackTrace();
                               mListMoviesDefaultView.onErrorInServer();
                           }

                           @Override
                           public void onComplete() {
                           }
                       };

    }

    public void getMovieDetails(int movieID, boolean isSaved, boolean isFavorite,  boolean dontIsFavorite, int status, String tag, int position) {
        this.mStatus = status;
        this.isSaved = isSaved;
        this.isFavorite = isFavorite;
        this.mPosition = position;
        this.dontIsFavorite = dontIsFavorite;

        if (mListMoviesDefaultView.isInternetConnected()) {
            mListMoviesDefaultView.showDialogProgress();
            mInterceptor.getMovieDetails(movieID, new String[]{})
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribe(new Observer<MovieDetails>() {
                                             @Override
                                             public void onSubscribe(Disposable d) {
                                                mSubscribes.add(d);
                                             }

                                             @Override
                                             public void onNext(MovieDetails value) {
                                                 onMovieDetailsReceived(value);
                                             }

                                             @Override
                                             public void onError(Throwable e) {
                                                mListMoviesDefaultView.onErrorInServer();
                                             }

                                             @Override
                                             public void onComplete() {}
                                         });

        } else {
            noConnectionError();
        }
    }

    public void onMovieDetailsReceived(MovieDetails movie) {
        long id = 0;
        boolean isDelete = false;

        if (dontIsFavorite) { //Removendo um Favorito
            mInterceptor.saveMovie(movie, isFavorite, mStatus, mProfileID)
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribe(onSavedMovie());

            mListMoviesDefaultView.onDeleteSaveSucess();
            isDelete = true;
        } else if (isSaved) { //Removendo um Filme Assistido
            mInterceptor.saveMovie(movie, isFavorite, mStatus, mProfileID)
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribe(onSavedMovie());

            mListMoviesDefaultView.onSucessSaveMovie();
        } else if ((!isSaved) && isFavorite) { //Adicionando um filme favorito
            mInterceptor.saveMovie(movie, isFavorite, mStatus, mProfileID)
                                         .observeOn(AndroidSchedulers.mainThread())
                                         .subscribe(onSavedMovie());

            mListMoviesDefaultView.onSucessSaveMovie();
        } else { //Senão, é pra deletar
            mSubscribes.add(mInterceptor.deleteMovie(movie, mProfileID)
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe());

            mListMoviesDefaultView.onDeleteSaveSucess();
            isDelete = true;
        }

        if (isDelete)
            mListMoviesDefaultView.notifyMovieRemoved(mPosition);
        else {
            mListIndex = 0;
            getMovies(mId, mTypeList, TAG, mDiscoverDTO);
        }

        mListMoviesDefaultView.hideDialogProgress();
    }

    private Observer<Long> onSavedMovie() {
        return new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribes.add(d);
            }

            @Override
            public void onNext(Long value) {
                if (value == -1)
                    mListMoviesDefaultView.onErrorSaveMovie();
            }

            @Override
            public void onError(Throwable e) {
                mListMoviesDefaultView.onErrorSaveMovie();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    @Override
    public void onUnbindView() {
        mSubscribes.clear();
        mListMoviesDefaultView = null;
    }

    private Observable<GenericListResponse<Movie>> getMoviesDB(Sort typeList) {
        mListMoviesDefaultView.setProgressVisibility(View.VISIBLE);
        mListMoviesDefaultView.setRecyclerViewVisibility(mListIndex == 0 ? View.GONE : View.VISIBLE);

        return mInterceptor.getMovieDB(typeList, mProfileID, ++mCurrentPage)
                           .observeOn(AndroidSchedulers.mainThread());
    }

    private void noConnectionError() {

        if (mListMoviesDefaultView.isAdded())
            mListMoviesDefaultView.onErrorNoConnection();

        mListMoviesDefaultView.setProgressVisibility(View.GONE);

        if (mCurrentPage == 0) {
            mListMoviesDefaultView.setRecyclerViewVisibility(View.GONE);
        }
    }

    public void onResponse(GenericListResponse<Movie> response) {
        mCurrentPage = response.getPage();
        mTotalPages = response.getTotalPage();

        if (mListMoviesDefaultView.isAdded()) {
            if (isFirstPage()) {
                mListMoviesDefaultView.setListMovies(DTOUtils.createMovieListDTO(response.getResults()), hasMorePages());
                mListMoviesDefaultView.setupRecyclerView();
            } else {
                mListMoviesDefaultView.addAllMovies(DTOUtils.createMovieListDTO(response.getResults()), hasMorePages());
                mListMoviesDefaultView.updateAdapter();
            }

            mListMoviesDefaultView.setRecyclerViewVisibility(View.VISIBLE);
            mListMoviesDefaultView.setProgressVisibility(View.GONE);
        }

    }

    private boolean isFirstPage() {
        return mCurrentPage == 1;
    }

    private boolean hasMorePages() {
        return mCurrentPage < mTotalPages;
    }
}
