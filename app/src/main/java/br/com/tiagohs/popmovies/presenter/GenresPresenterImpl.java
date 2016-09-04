package br.com.tiagohs.popmovies.presenter;

import android.util.Log;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.response.GenresResponse;
import br.com.tiagohs.popmovies.server.PopMovieServer;
import br.com.tiagohs.popmovies.server.ResponseListener;
import br.com.tiagohs.popmovies.view.GenresView;

/**
 * Created by Tiago Henrique on 03/09/2016.
 */
public class GenresPresenterImpl implements GenresPresenter, ResponseListener<GenresResponse> {

    private GenresView mGenresView;
    private PopMovieServer mPopMovieServer;
    private static Map<Integer, Integer> mBackgroundGeners;

    static {
        mBackgroundGeners = new HashMap<>();

        mBackgroundGeners.put(28, R.drawable.img_gener_action);
        mBackgroundGeners.put(12, R.drawable.img_gener_adventure);
        mBackgroundGeners.put(16, R.drawable.img_gener_animation);
        mBackgroundGeners.put(35, R.drawable.img_gener_comedy);
        mBackgroundGeners.put(80, R.drawable.img_gener_crime);
        mBackgroundGeners.put(99, R.drawable.img_gener_documentary);
        mBackgroundGeners.put(18, R.drawable.img_gener_drama);
        mBackgroundGeners.put(10751, R.drawable.img_gener_family);
        mBackgroundGeners.put(14, R.drawable.img_gener_fantasy);
        mBackgroundGeners.put(10769, R.drawable.img_gener_foreign);
        mBackgroundGeners.put(36, R.drawable.img_gener_history);
        mBackgroundGeners.put(27, R.drawable.img_gener_horror);
        mBackgroundGeners.put(10402, R.drawable.img_gener_music);
        mBackgroundGeners.put(9648, R.drawable.img_gener_mistery);
        mBackgroundGeners.put(10749, R.drawable.img_gener_romance);
        mBackgroundGeners.put(878, R.drawable.img_gener_science_fiction);
        mBackgroundGeners.put(10770, R.drawable.img_gener_tv_movie);
        mBackgroundGeners.put(53, R.drawable.img_gener_thriller);
        mBackgroundGeners.put(10752, R.drawable.img_gener_war);
        mBackgroundGeners.put(37, R.drawable.img_gener_westeron);
    }

    public GenresPresenterImpl() {
        mPopMovieServer = PopMovieServer.getInstance();
    }

    @Override
    public void setView(GenresView view) {
        this.mGenresView = view;
    }


    @Override
    public void getGenres() {
        mPopMovieServer.getGenres(this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(GenresResponse response) {

        for (Map.Entry<Integer, Integer> entry : mBackgroundGeners.entrySet()) {
            for (int cont = 0; cont < response.getGenres().size(); cont++) {
                if (response.getGenres().get(cont).getId() == entry.getKey()) {
                    Log.i("Genre: ", response.getGenres().get(cont).getName());
                    response.getGenres().get(cont).setImgPath(entry.getValue());
                    break;
                }
            }
        }

        mGenresView.updateView(response.getGenres());

    }
}
