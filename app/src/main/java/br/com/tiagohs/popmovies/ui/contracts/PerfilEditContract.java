package br.com.tiagohs.popmovies.ui.contracts;

import java.util.Calendar;

import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.ui.presenter.IPresenter;
import br.com.tiagohs.popmovies.ui.view.IView;
import io.reactivex.Observable;

public class PerfilEditContract {

    public interface PerfilEditInterceptor {

        Observable<ProfileDB> findProfileByUserUsername(String username);
        Observable<Long> saveProfile(ProfileDB profile);
    }

    public interface PerfilEditPresenter extends IPresenter<PerfilEditView> {

        void getProfileInfo(String username);
        void save(String name, Calendar birthday, String country, String gender, String descricao, String photo);

    }

    public interface PerfilEditView extends IView {

        void setName(String name);
        void setBirthday(Calendar birthday);
        void setCountry(String country);
        void setDescricao(String descricao);
        void setPhoto(String path, String name);
        void setLocalPhoto();
    }

}
