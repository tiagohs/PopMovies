package br.com.tiagohs.popmovies.ui.presenter;

import android.view.View;

import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.ui.contracts.PersonDetailContract;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.SubMethod;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

public class PersonDetailPresenter implements PersonDetailContract.PersonDetailPresenter {
    private static final String LOCALE_US = "en";

    private PersonDetailContract.PersonDetailView mPersonDetailView;
    private PersonDetailContract.PersonDetailInterceptor mInterceptor;

    private final String[] mAppendToResponseValues = new String[]{SubMethod.MOVIE_CREDITS.getValue(),
                                                                  SubMethod.COMBINED_CREDITS.getValue(),
                                                                  SubMethod.EXTERNAL_IDS.getValue(),
                                                                  SubMethod.IMAGES.getValue(),
                                                                  SubMethod.TAGGED_IMAGES.getValue()};

    private CompositeDisposable mSubscribers;

    public PersonDetailPresenter(PersonDetailContract.PersonDetailInterceptor interceptor, CompositeDisposable subscribers) {
        mInterceptor = interceptor;
        mSubscribers = subscribers;
    }

    @Override
    public void getPersonDetails(int personID) {

        if (mPersonDetailView.isInternetConnected()) {
            Observable<PersonInfo> observableCurrentLanguage = mInterceptor.getPersonDetails(personID, mAppendToResponseValues);
            Observable<PersonInfo> observableUsLanguage = mInterceptor.getPersonDetails(personID, mAppendToResponseValues, LOCALE_US);

            Observable.zip(observableCurrentLanguage, observableUsLanguage, new BiFunction<PersonInfo, PersonInfo, PersonInfo>() {
                                @Override
                                public PersonInfo apply(PersonInfo currentLanguagePersonInfo, PersonInfo usLanguagePersonInfo2) throws Exception {

                                    if (ViewUtils.isEmptyValue(currentLanguagePersonInfo.getBiography())) {
                                        currentLanguagePersonInfo.setBiography(usLanguagePersonInfo2.getBiography());
                                    }

                                    return currentLanguagePersonInfo;
                                }
                            })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(onObserverPersonDetail());
        } else
            noConnectionError();

    }

    private Observer<PersonInfo> onObserverPersonDetail() {
        return new Observer<PersonInfo>() {
            @Override
            public void onSubscribe(Disposable d) {
                mSubscribers.add(d);
            }

            @Override
            public void onNext(PersonInfo personInfo) {
                mPersonDetailView.setPerson(personInfo);
                mPersonDetailView.updateImages();

                personInfo.setMoviesCarrer(personInfo.getMoviesCarrer());
                mPersonDetailView.updateAditionalInfo(personInfo.getMoviesCarrer().size(), (personInfo.getImages().size() + personInfo.getTaggedImages().size()));

                updateExternalLinks(personInfo);
                mPersonDetailView.setupTabs();
                mPersonDetailView.setProgressVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mPersonDetailView.onErrorInServer();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void noConnectionError() {

        mPersonDetailView.onErrorNoConnection();
        mPersonDetailView.setProgressVisibility(View.GONE);
    }

    @Override
    public void onBindView(PersonDetailContract.PersonDetailView view) {
        mPersonDetailView = view;
    }

    @Override
    public void onUnbindView() {
        mSubscribers.clear();
        mPersonDetailView = null;
    }

    private void updateExternalLinks(PersonInfo personInfo) {

        if (personInfo.getExternalIDs().getFacebookId() != null)
            mPersonDetailView.setVisibilityFacebook(View.VISIBLE);

        if (personInfo.getExternalIDs().getTwitterId() != null)
            mPersonDetailView.setVisibilityTwitter(View.VISIBLE);

        if (personInfo.getExternalIDs().getInstagramID() != null)
            mPersonDetailView.setVisibilityInstagram(View.VISIBLE);

    }

}
