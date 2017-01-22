package br.com.tiagohs.popmovies.view;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.Calendar;

/**
 * Created by Tiago on 21/01/2017.
 */

public interface PerfilEditView extends BaseView {

    void setName(String name);
    void setBirthday(Calendar birthday);
    void setCountry(String country);
    void setDescricao(String descricao);
    void setPhoto(String path);
    void setLocalPhoto(Bitmap bitmap);
}
