package br.com.tiagohs.popmovies.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Calendar;

import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.data.repository.ProfileRepository;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.view.PerfilEditView;

public interface PerfilEditPresenter extends BasePresenter<PerfilEditView> {

    void getProfileInfo();
    void save(String name, Calendar birthday, String country, String gender, String descricao, String photo);

    void setProfileRepository(ProfileRepository profileRepository);
    void setUsername(String username);
}
