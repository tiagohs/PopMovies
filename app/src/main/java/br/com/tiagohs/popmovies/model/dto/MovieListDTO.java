package br.com.tiagohs.popmovies.model.dto;

public class MovieListDTO {
    private int mMovieID;
    private String movieName;
    private String mPosterPath;
    private String voteAverage;
    private boolean jaAssistido;
    private boolean favorito;

    public MovieListDTO(int movieID, String movieName, String posterPath, String voteAverage, boolean jaAssistido, boolean favorito) {
        mMovieID = movieID;
        this.movieName = movieName;
        mPosterPath = posterPath;
        this.voteAverage = voteAverage;
        this.jaAssistido = jaAssistido;
        this.favorito = favorito;
    }

    public MovieListDTO() {
    }

    public int getmMovieID() {
        return mMovieID;
    }

    public void setmMovieID(int mMovieID) {
        this.mMovieID = mMovieID;
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
}
