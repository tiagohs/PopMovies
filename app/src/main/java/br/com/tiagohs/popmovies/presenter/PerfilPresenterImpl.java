package br.com.tiagohs.popmovies.presenter;

import android.view.View;

import com.android.volley.VolleyError;

import java.util.List;
import java.util.Random;

import br.com.tiagohs.popmovies.data.repository.ProfileRepository;
import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.server.methods.MoviesServer;
import br.com.tiagohs.popmovies.view.PerfilView;

public class PerfilPresenterImpl implements PerfilPresenter, ResponseListener<ImageResponse> {
    private static final String TAG = PerfilPresenterImpl.class.getSimpleName();

    private PerfilView mPerfilView;

    private ProfileRepository mProfileRepository;
    private String mUsername;

    private ProfileDB mProfile;
    private MoviesServer mMoviesServer;
    private String mTag;

    public PerfilPresenterImpl() {
        mMoviesServer = new MoviesServer();
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void setProfileRepository(ProfileRepository profileRepository) {
        this.mProfileRepository = profileRepository;
    }

    public void initUpdates(String tag) {
        mTag = tag;
        mProfile = mProfileRepository.findProfileByUserUsername(mUsername);

        mPerfilView.setProfile(mProfile);
        List<MovieDB> movies = mProfile.getFilmes();

        if (movies != null) {
            if (!movies.isEmpty()) {
                getRandomBackground();
            }
        }

        if (mProfile.getUser().getTypePhoto() == UserDB.PHOTO_ONLINE)
            mPerfilView.setImagePerfil(mProfile.getUser().getPicturePath());
        else if (mProfile.getUser().getTypePhoto() == UserDB.PHOTO_LOCAL)
            mPerfilView.setLocalImagePerfil();

        mPerfilView.setNamePerfil(mProfile.getUser().getNome());
        mPerfilView.setupTabs();
        mPerfilView.setProgressVisibility(View.GONE);
    }

    private void getRandomBackground() {
        int index = new Random().nextInt(mProfile.getFilmes().size());
        getMovie(mProfile.getFilmes().get(index).getIdServer(), mTag);
    }

    private void getMovie(int movieID, String tag) {
        mMoviesServer.getMovieImagens(movieID, tag, this);
    }

    @Override
    public void setView(PerfilView view) {
        mPerfilView = view;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mPerfilView.onErrorLoadingBackground();
    }

    @Override
    public void onResponse(ImageResponse response) {

        if (hasImages(response.getBackdrops(), response.getPosters())) {
            if (mProfile.getFilmesAssistidos() != null)
                getRandomBackground();

        } else {

            if (!response.getBackdrops().isEmpty())
                updatePerfilBackground(response.getBackdrops());
            else
                updatePerfilBackground(response.getPosters());

        }

    }

    private boolean hasImages(List<Artwork> backdrops, List<Artwork> posters) {
        return backdrops.isEmpty() && posters.isEmpty();
    }

    private void updatePerfilBackground(List<Artwork> images) {
        int index = new Random().nextInt(images.size());
        mPerfilView.setBackground(images.get(index).getFilePath());
    }


}
