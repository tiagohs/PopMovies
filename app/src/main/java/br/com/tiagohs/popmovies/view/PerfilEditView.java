package br.com.tiagohs.popmovies.view;

import java.util.Calendar;

public interface PerfilEditView extends BaseView {

    void setName(String name);
    void setBirthday(Calendar birthday);
    void setCountry(String country);
    void setDescricao(String descricao);
    void setPhoto(String path);
    void setLocalPhoto();
}
