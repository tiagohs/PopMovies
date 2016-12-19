package br.com.tiagohs.popmovies.model.db;


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

    public MovieDB() {
    }

    public MovieDB(int idServer, String posterPath, boolean favorite, double vote, String title, Calendar dateSaved, long profileID, int duracao) {
        this.idServer = idServer;
        this.posterPath = posterPath;
        this.favorite = favorite;
        this.vote = vote;
        this.title = title;
        this.dateSaved = dateSaved;
        this.duracao = duracao;
        this.profileID = profileID;
    }

    public MovieDB(long aLong, long aLong1, String string, boolean b, double aDouble) {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
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

    public Calendar getDateSaved() {
        return dateSaved;
    }

    public void setDateSaved(Calendar dateSaved) {
        this.dateSaved = dateSaved;
    }

    public long getProfileID() {
        return profileID;
    }

    public void setProfileID(long profileID) {
        this.profileID = profileID;
    }
}
