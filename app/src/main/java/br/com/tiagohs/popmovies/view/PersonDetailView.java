package br.com.tiagohs.popmovies.view;

import br.com.tiagohs.popmovies.model.person.PersonInfo;

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
