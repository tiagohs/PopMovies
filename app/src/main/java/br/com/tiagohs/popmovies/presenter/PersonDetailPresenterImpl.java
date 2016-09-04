package br.com.tiagohs.popmovies.presenter;

import com.android.volley.VolleyError;

import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.util.enumerations.SubMethod;
import br.com.tiagohs.popmovies.view.PersonDetailView;

/**
 * Created by Tiago Henrique on 03/09/2016.
 */
public class PersonDetailPresenterImpl implements PersonDetailPresenter, ResponseListener<PersonInfo> {

    private PersonDetailView mPersonDetailView;
    private PopMovieServer mPopMovieServer;

    public PersonDetailPresenterImpl() {
        mPopMovieServer = PopMovieServer.getInstance();
    }

    @Override
    public void getPersonDeatils(int personID) {
        mPopMovieServer.getPersonDetails(personID, new String[]{SubMethod.MOVIE_CREDITS.getValue(),
                                                                SubMethod.COMBINED_CREDITS.getValue(),
                                                                SubMethod.EXTERNAL_IDS.getValue(),
                                                                SubMethod.IMAGES.getValue(),
                                                                SubMethod.TAGGED_IMAGES.getValue()}, this);
    }

    @Override
    public void setView(PersonDetailView view) {
        mPersonDetailView = view;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(PersonInfo response) {
        mPersonDetailView.atualizarView(response);
    }
}
