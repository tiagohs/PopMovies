package br.com.tiagohs.popmovies.view;

import android.graphics.Typeface;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.util.ImageUtils;

public interface PerfilView extends BaseView {

    void setNamePerfil(String nameUser);
    void setImagePerfil(String imagePath);
    void setBackground(String backgroundPath);
    void setupTabs();
    void setProfile(ProfileDB mProfile);
    void setPerfilDescricao(String descricao);
}
