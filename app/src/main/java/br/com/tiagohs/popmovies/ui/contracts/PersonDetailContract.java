package br.com.tiagohs.popmovies.ui.contracts;

import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.ui.presenter.BasePresenter;
import br.com.tiagohs.popmovies.ui.view.BaseView;
import io.reactivex.Observable;

public class PersonDetailContract {

    public interface PersonDetailInterceptor {

        Observable<PersonInfo> getPersonDetails(int personID, String[] appendToResponse);
        Observable<PersonInfo> getPersonDetails(int personID, String[] appendToResponse, String language);
    }

    public interface PersonDetailPresenter extends BasePresenter<PersonDetailView> {

        void getPersonDetails(int personID);
    }

    public interface PersonDetailView extends BaseView {

        void updateImages();
        void updateAditionalInfo(int totalFilmes, int totalFotos);
        void setupTabs();

        boolean isDestroyed();
        void setPerson(PersonInfo person);

        void setVisibilityFacebook(int visibility);
        void setVisibilityTwitter(int visibility);
        void setVisibilityInstagram(int visibility);
    }


}
