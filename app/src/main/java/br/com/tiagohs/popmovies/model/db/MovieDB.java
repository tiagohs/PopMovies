package br.com.tiagohs.popmovies.model.db;

/**
 * Created by Tiago Henrique on 29/10/2016.
 */

public class MovieDB {
    private long id;
    private long idServer;
    private String posterPath;
    private boolean favorite;
    private double vote;

    public MovieDB() {
    }

    public MovieDB(long idServer, String posterPath, boolean favorite, double vote) {
        this.idServer = idServer;
        this.posterPath = posterPath;
        this.favorite = favorite;
        this.vote = vote;
    }

    public MovieDB(long id, long idServer, String posterPath, boolean favorite, double vote) {
        this.id = id;
        this.idServer = idServer;
        this.posterPath = posterPath;
        this.favorite = favorite;
        this.vote = vote;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdServer() {
        return idServer;
    }

    public void setIdServer(long idServer) {
        this.idServer = idServer;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
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
}
