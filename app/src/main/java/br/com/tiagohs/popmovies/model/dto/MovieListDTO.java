package br.com.tiagohs.popmovies.model.dto;

public class MovieListDTO {
    private int mMovieID;
    private String mPosterPath;
    private String voteAverage;

    public MovieListDTO(int movieID, String posterPath, String voteAverage) {
        mMovieID = movieID;
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
}
