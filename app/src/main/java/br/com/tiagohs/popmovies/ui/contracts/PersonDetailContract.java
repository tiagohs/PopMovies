package br.com.tiagohs.popmovies.ui.contracts;

import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.ui.presenter.IPresenter;
import br.com.tiagohs.popmovies.ui.view.IView;
import io.reactivex.Observable;

public class PersonDetailContract {

    public interface PersonDetailInterceptor {

        Observable<PersonInfo> getPersonDetails(int personID, String[] appendToResponse);
    }

    public interface PersonDetailPresenter extends IPresenter<PersonDetailView> {

        void getPersonDetails(int personID);
    }

    public interface PersonDetailView extends IView {

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
