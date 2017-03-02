package br.com.tiagohs.popmovies.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieListDTO implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mMovieID);
        dest.writeString(this.creditID);
        dest.writeString(this.movieName);
        dest.writeString(this.mPosterPath);
        dest.writeString(this.voteAverage);
        dest.writeByte(this.jaAssistido ? (byte) 1 : (byte) 0);
        dest.writeByte(this.favorito ? (byte) 1 : (byte) 0);
    }

    protected MovieListDTO(Parcel in) {
        this.mMovieID = in.readInt();
        this.creditID = in.readString();
        this.movieName = in.readString();
        this.mPosterPath = in.readString();
        this.voteAverage = in.readString();
        this.jaAssistido = in.readByte() != 0;
        this.favorito = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MovieListDTO> CREATOR = new Parcelable.Creator<MovieListDTO>() {
        @Override
        public MovieListDTO createFromParcel(Parcel source) {
            return new MovieListDTO(source);
        }

        @Override
        public MovieListDTO[] newArray(int size) {
            return new MovieListDTO[size];
        }
    };
}
