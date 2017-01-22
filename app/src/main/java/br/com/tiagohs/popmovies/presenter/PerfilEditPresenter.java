package br.com.tiagohs.popmovies.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Calendar;

import br.com.tiagohs.popmovies.view.PerfilEditView;

public interface PerfilEditPresenter extends BasePresenter<PerfilEditView> {
    void setContext(Context context);
    void getProfileInfo();

    void save(String name, Calendar birthday, String country, String gender, String descricao, String photo);
}
