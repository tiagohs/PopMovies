package br.com.tiagohs.popmovies.model.dto;

public class MovieListDTO {
    private int mMovieID;
    private String movieName;
    private String mPosterPath;
    private String voteAverage;

    public MovieListDTO(int movieID, String movieName, String posterPath, String voteAverage) {
        mMovieID = movieID;
        this.movieName = movieName;
        mPosterPath = posterPath;
        this.voteAverage = voteAverage;
    }

    public MovieListDTO() {
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
