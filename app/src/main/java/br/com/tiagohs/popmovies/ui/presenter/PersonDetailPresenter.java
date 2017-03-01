package br.com.tiagohs.popmovies.ui.presenter;

import android.view.View;

import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.ui.contracts.PersonDetailContract;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import br.com.tiagohs.popmovies.util.enumerations.SubMethod;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class PersonDetailPresenter extends BasePresenter<PersonDetailContract.PersonDetailView, PersonDetailContract.PersonDetailInterceptor> implements PersonDetailContract.PersonDetailPresenter {
    private final String[] mAppendToResponseValues = new String[]{SubMethod.MOVIE_CREDITS.getValue(),
                                                                  SubMethod.COMBINED_CREDITS.getValue(),
                                                                  SubMethod.EXTERNAL_IDS.getValue(),
                                                                  SubMethod.IMAGES.getValue(),
                                                                  SubMethod.TAGGED_IMAGES.getValue()};

    public PersonDetailPresenter(PersonDetailContract.PersonDetailInterceptor interceptor, CompositeDisposable subscribers) {
        super(interceptor, subscribers);
    }

    @Override
    public void getPersonDetails(int personID) {

        if (mView.isInternetConnected()) {
            mInterceptor.getPersonDetails(personID, mAppendToResponseValues)
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
                mView.setPerson(personInfo);
                mView.updateImages();

                personInfo.setMoviesCarrer(personInfo.getMoviesCarrer());
                mView.updateAditionalInfo(personInfo.getMoviesCarrer().size(), (personInfo.getImages().size() + personInfo.getTaggedImages().size()));

                updateExternalLinks(personInfo);
                mView.setupTabs();
                mView.setProgressVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mView.onErrorInServer();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void updateExternalLinks(PersonInfo personInfo) {

        if (!EmptyUtils.isEmpty(personInfo.getExternalIDs().getFacebookId()))
            mView.setVisibilityFacebook(View.VISIBLE);

        if (!EmptyUtils.isEmpty(personInfo.getExternalIDs().getTwitterId()))
            mView.setVisibilityTwitter(View.VISIBLE);

        if (!EmptyUtils.isEmpty(personInfo.getExternalIDs().getInstagramID()))
            mView.setVisibilityInstagram(View.VISIBLE);

    }

}
