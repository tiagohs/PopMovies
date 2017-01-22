package br.com.tiagohs.popmovies.model.db;


import java.util.Calendar;
import java.util.List;

public class MovieDB {
    public static final int STATUS_WATCHED = 0;
    public static final int STATUS_WANT_SEE = 1;
    public static final int STATUS_DONT_WANT_SEE = 2;

    private long id;
    private int idServer;
    private int status;
    private int runtime;
    private String posterPath;
    private String title;
    private boolean favorite;
    private double vote;
    private long profileID;
    private Calendar dateSaved;
    private Calendar releaseDate;
    private int releaseYear;
    private List<GenreDB> genres;

    public MovieDB() {
    }

    public MovieDB(int idServer, int status, int runtime, String posterPath, String title, boolean favorite, double vote, long profileID, Calendar dateSaved, Calendar releaseDate, int releaseYear, List<GenreDB> genres) {
        this.idServer = idServer;
        this.status = status;
        this.runtime = runtime;
        this.posterPath = posterPath;
        this.title = title;
        this.favorite = favorite;
        this.vote = vote;
        this.profileID = profileID;
        this.dateSaved = dateSaved;
        this.releaseDate = releaseDate;
        this.releaseYear = releaseYear;
        this.genres = genres;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIdServer() {
        return idServer;
    }

    public void setIdServer(int idServer) {
        this.idServer = idServer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public double getVote() {
        return vote;
    }

    public void setVote(double vote) {
        this.vote = vote;
    }

    public long getProfileID() {
        return profileID;
    }

    public void setProfileID(long profileID) {
        this.profileID = profileID;
    }

    public Calendar getDateSaved() {
        return dateSaved;
    }

    public void setDateSaved(Calendar dateSaved) {
        this.dateSaved = dateSaved;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<GenreDB> getGenres() {
        return genres;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setGenres(List<GenreDB> genres) {
        this.genres = genres;
    }


}
