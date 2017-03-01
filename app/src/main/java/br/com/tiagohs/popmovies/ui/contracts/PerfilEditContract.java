package br.com.tiagohs.popmovies.ui.contracts;

import java.util.Calendar;

import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.ui.presenter.BasePresenter;
import br.com.tiagohs.popmovies.ui.view.BaseView;
import io.reactivex.Observable;

/**
 * Created by Tiago on 25/02/2017.
 */

public class PerfilEditContract {

    public interface PerfilEditInterceptor {

        Observable<ProfileDB> findProfileByUserUsername(String username);
        Observable<Long> saveProfile(ProfileDB profile);
    }

    public interface PerfilEditPresenter extends BasePresenter<PerfilEditView> {

        void getProfileInfo(String username);
        void save(String name, Calendar birthday, String country, String gender, String descricao, String photo);

    }

    public interface PerfilEditView extends BaseView {

        void setName(String name);
        void setBirthday(Calendar birthday);
        void setCountry(String country);
        void setDescricao(String descricao);
        void setPhoto(String path);
        void setLocalPhoto();
    }

}
