package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;
import android.view.View;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.methods.PersonsServer;
import br.com.tiagohs.popmovies.util.LocaleUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.enumerations.SubMethod;
import br.com.tiagohs.popmovies.view.PersonDetailView;

public class PersonDetailPresenterImpl implements PersonDetailPresenter, ResponseListener<PersonInfo> {

    private PersonDetailView mPersonDetailView;
    private PersonsServer mPersonsServer;

    private int mPersonID;
    private String mTag;

    public PersonDetailPresenterImpl() {
        mPersonsServer = new PersonsServer();
    }

    @Override
    public void getPersonDetails(int personID, String tag) {
        mPersonID = personID;
        mTag = tag;

        if (mPersonDetailView.isInternetConnected()) {
            mPersonDetailView.setProgressVisibility(View.VISIBLE);
            mPersonsServer.getPersonDetails(personID, new String[]{SubMethod.MOVIE_CREDITS.getValue(),
                    SubMethod.COMBINED_CREDITS.getValue(),
                    SubMethod.EXTERNAL_IDS.getValue(),
                    SubMethod.IMAGES.getValue(),
                    SubMethod.TAGGED_IMAGES.getValue()}, tag, LocaleUtils.getLocaleLanguageAndCountry(), this);
        } else {
            noConnectionError();
        }

    }

    private void noConnectionError() {

        mPersonDetailView.onError("Sem Conexão");
        mPersonDetailView.setProgressVisibility(View.GONE);
    }

    @Override
    public void setView(PersonDetailView view) {
        mPersonDetailView = view;
    }

    @Override
    public void onCancellRequest(Activity activity, String tag) {
        ((App) activity.getApplication()).cancelAll(tag);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (mPersonDetailView.isAdded())
            mPersonDetailView.onError("Houve algum erro.");
    }

    @Override
    public void onResponse(PersonInfo personInfo) {

        if (!mPersonDetailView.isDestroyed()) {

            if (MovieUtils.isEmptyValue(personInfo.getBiography())) {
                mPersonsServer.getPersonDetails(mPersonID, new String[]{SubMethod.MOVIE_CREDITS.getValue(),
                        SubMethod.COMBINED_CREDITS.getValue(),
                        SubMethod.EXTERNAL_IDS.getValue(),
                        SubMethod.IMAGES.getValue(),
                        SubMethod.TAGGED_IMAGES.getValue()}, mTag, "en", this);
            } else {
                mPersonDetailView.setPerson(personInfo);

                mPersonDetailView.updateImages();
                mPersonDetailView.updateAditionalInfo(personInfo.getMovieCredits().getCast().size() + personInfo.getMovieCredits().getCrew().size(),
                        personInfo.getImages().size() + personInfo.getTaggedImages().size());
                updateExternalLinks(personInfo);
                mPersonDetailView.setupTabs();
                mPersonDetailView.setProgressVisibility(View.GONE);
            }
        }

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
