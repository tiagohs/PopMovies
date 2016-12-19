package br.com.tiagohs.popmovies.view;

import android.graphics.Typeface;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.util.ImageUtils;

public interface PerfilView extends BaseView {

    void setNamePerfil(String nameUser);
    void setEmailPerfil(String emailUser);
    void setImagePerfil(String imagePath);
    void setTotalHorasAssistidas(int duracaoTotal);
    void setTotalFilmesAssistidos(int totalFilmesAssistidos);
    void setBackground(String backgroundPath);
    void setupTabs();
}
