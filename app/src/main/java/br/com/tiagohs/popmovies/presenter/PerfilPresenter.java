package br.com.tiagohs.popmovies.presenter;

import br.com.tiagohs.popmovies.data.repository.ProfileRepository;
import br.com.tiagohs.popmovies.view.PerfilView;

public interface PerfilPresenter extends BasePresenter<PerfilView> {

    void setUsername(String username);
    void setProfileRepository(ProfileRepository profileRepository);
    void initUpdates(String tag);
}
