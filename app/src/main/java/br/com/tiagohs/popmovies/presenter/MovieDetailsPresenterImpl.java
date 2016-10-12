package br.com.tiagohs.popmovies.presenter;

import android.view.View;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.interceptor.VideoInterceptor;
import br.com.tiagohs.popmovies.interceptor.VideoInterceptorImpl;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCrew;
import br.com.tiagohs.popmovies.model.dto.ItemListDTO;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.enumerations.SubMethod;
import br.com.tiagohs.popmovies.view.MovieDetailsView;

public class MovieDetailsPresenterImpl implements MovieDetailsPresenter, VideoInterceptor.onVideoListener {
    private MovieDetailsView mMovieDetailsView;

    private PopMovieServer mPopMovieServer;
    private VideoInterceptor mVideoInterceptor;

    private String mOriginalLanguage;

    private MovieDetails mMovieDetails;
    private int mMovieID;

    private String[] mMovieParameters = new String[]{SubMethod.CREDITS.getValue(), SubMethod.RELEASE_DATES.getValue(),
            SubMethod.SIMILAR.getValue(), SubMethod.KEYWORDS.getValue(),
            SubMethod.VIDEOS.getValue(), SubMethod.TRANSLATIONS.getValue()};

    public MovieDetailsPresenterImpl() {
        mPopMovieServer = PopMovieServer.getInstance();
        mVideoInterceptor = new VideoInterceptorImpl(this);
    }

    @Override
    public void setView(MovieDetailsView view) {
        this.mMovieDetailsView = view;
    }

    @Override
    public void getMovieDetails(int movieID) {
        mMovieID = movieID;
        mMovieDetailsView.setProgressVisibility(View.VISIBLE);

        if (mMovieDetailsView.isInternetConnected()) {
            mPopMovieServer.getMovieDetails(movieID, mMovieParameters, this);
            mMovieDetailsView.setTabsVisibility(View.GONE);
        } else {
            noConnectionError();
        }

    }

    private void noConnectionError() {
        if (mMovieDetailsView.isAdded()) {
            mMovieDetailsView.onError("Sem Conexao");
            mMovieDetailsView.setProgressVisibility(View.GONE);
        }

    }

    public void getVideos() {
        mVideoInterceptor.getVideos(mMovieDetails.getId(), mMovieDetails.getTranslations());
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        noConnectionError();
    }

    @Override
    public void onResponse(MovieDetails response) {
        if (mOriginalLanguage == null)
            mMovieDetails = response;

        if (mMovieDetailsView.isAdded()) {
            if ((MovieUtils.isEmptyValue(response.getOverview()) || response.getRuntime() == 0) && mOriginalLanguage == null) {

                mOriginalLanguage = response.getOriginalLanguage();
                mPopMovieServer.getMovieDetails(mMovieID, mMovieParameters, mOriginalLanguage, this);

            } else {

                if (mOriginalLanguage != null) {
                    if (MovieUtils.isEmptyValue(mMovieDetails.getOverview())) {
                        mMovieDetails.setOverview(response.getOverview());
                    }
                    if (mMovieDetails.getRuntime() == 0)
                        mMovieDetails.setRuntime(response.getRuntime());

                    initUpdates(mMovieDetails);
                } else {
                    initUpdates(response);
                }

            }
        }
    }

    private void initUpdates(MovieDetails movieDetails) {
        if (movieDetails.getVideos().isEmpty())
            getVideos();

        mMovieDetailsView.setupDirectorsRecyclerView(getDirectorsDTO(movieDetails.getCrew()));
        mMovieDetailsView.updateUI(movieDetails);
        mMovieDetailsView.setupTabs();
        mMovieDetailsView.setProgressVisibility(View.GONE);
        mMovieDetailsView.setTabsVisibility(View.VISIBLE);
    }

    private List<ItemListDTO> getDirectorsDTO(List<MediaCreditCrew> crews) {
        List<ItemListDTO> list = new ArrayList<>();

        for (MediaCreditCrew crew : crews) {
            if (crew.getDepartment().equals("Directing") && !list.contains(crew))
                list.add(new ItemListDTO(crew.getId(), crew.getName()));
        }

        return list;
    }


    @Override
    public void onVideoRequestSucess(VideosResponse videosResponse) {
        if (!videosResponse.getVideos().isEmpty())
            mMovieDetailsView.updateVideos(videosResponse);
        else
            mMovieDetailsView.setPlayButtonVisibility(View.GONE);
    }

    @Override
    public void onVideoRequestError(VolleyError error) {
        noConnectionError();
    }

    @Override
    public boolean isAdded() {
        return mMovieDetailsView.isAdded();
    }
}
