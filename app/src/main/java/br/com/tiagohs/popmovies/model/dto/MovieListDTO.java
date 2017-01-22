package br.com.tiagohs.popmovies.model.dto;

import android.util.Log;

import java.io.Serializable;

public class MovieListDTO implements Serializable {
    private int mMovieID;
    private String creditID;
    private String movieName;
    private String mPosterPath;
    private String voteAverage;
    private boolean jaAssistido;
    private boolean favorito;

    public MovieListDTO(int movieID, String movieName, String posterPath, String voteAverage, boolean jaAssistido, boolean favorito) {
        this.mMovieID = movieID;
        this.movieName = movieName;
        this.mPosterPath = posterPath;
        this.voteAverage = voteAverage;
        this.jaAssistido = jaAssistido;
        this.favorito = favorito;
    }

    public MovieListDTO() {
    }

    public MovieListDTO(int id, String title, String posterPath, String voteAverage) {
        this.mMovieID = id;
        this.movieName = title;
        this.mPosterPath = posterPath;
        this.voteAverage = voteAverage;
    }

    public String getCreditID() {
        return creditID;
    }

    public void setCreditID(String creditID) {
        this.creditID = creditID;
    }

    public String getmPosterPath() {
        return mPosterPath;
    }

    public void setmPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public boolean isJaAssistido() {
        return jaAssistido;
    }

    public void setJaAssistido(boolean jaAssistido) {
        this.jaAssistido = jaAssistido;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public int getMovieID() {
        return mMovieID;
    }

    public void setMovieID(int movieID) {
        mMovieID = movieID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof MovieListDTO) {
            MovieListDTO m = (MovieListDTO) obj;
            //Log.i("MovieL", m.getMovieName() + " ID " + m.getMovieID() + " " + getMovieName() + " ID2: " + getMovieID());
            return m.getMovieID() == this.getMovieID();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
