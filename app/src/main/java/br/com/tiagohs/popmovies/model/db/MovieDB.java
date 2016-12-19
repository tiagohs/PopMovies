package br.com.tiagohs.popmovies.model.db;

<<<<<<< HEAD
import java.util.Calendar;

public class MovieDB {
    private long id;
    private int idServer;
    private int duracao;
    private String posterPath;
    private String title;
    private boolean favorite;
    private double vote;
    private Calendar dateSaved;
    private long profileID;
=======
/**
 * Created by Tiago Henrique on 29/10/2016.
 */

public class MovieDB {
    private long id;
    private long idServer;
    private String posterPath;
    private boolean favorite;
    private double vote;
>>>>>>> origin/master

    public MovieDB() {
    }

<<<<<<< HEAD
    public MovieDB(int idServer, String posterPath, boolean favorite, double vote, String title, Calendar dateSaved, long profileID, int duracao) {
=======
    public MovieDB(long idServer, String posterPath, boolean favorite, double vote) {
>>>>>>> origin/master
        this.idServer = idServer;
        this.posterPath = posterPath;
        this.favorite = favorite;
        this.vote = vote;
<<<<<<< HEAD
        this.title = title;
        this.dateSaved = dateSaved;
        this.duracao = duracao;
        this.profileID = profileID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
=======
    }

    public MovieDB(long id, long idServer, String posterPath, boolean favorite, double vote) {
        this.id = id;
        this.idServer = idServer;
        this.posterPath = posterPath;
        this.favorite = favorite;
        this.vote = vote;
>>>>>>> origin/master
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

<<<<<<< HEAD
    public int getIdServer() {
        return idServer;
    }

    public void setIdServer(int idServer) {
=======
    public long getIdServer() {
        return idServer;
    }

    public void setIdServer(long idServer) {
>>>>>>> origin/master
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
<<<<<<< HEAD

    public Calendar getDateSaved() {
        return dateSaved;
    }

    public void setDateSaved(Calendar dateSaved) {
        this.dateSaved = dateSaved;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public long getProfileID() {
        return profileID;
    }

    public void setProfileID(long profileID) {
        this.profileID = profileID;
    }
=======
>>>>>>> origin/master
}
