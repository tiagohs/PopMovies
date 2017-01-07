package br.com.tiagohs.popmovies.model.db;

/**
 * Created by Tiago on 06/01/2017.
 */

public class GenreDB {
    private long id;
    private int genrerID;
    private String genrerName;

    public GenreDB(long id, int genrerID, String genrerName) {
        this.id = id;
        this.genrerID = genrerID;
        this.genrerName = genrerName;
    }

    public GenreDB(long id, int genrerID) {
        this.id = id;
        this.genrerID = genrerID;
    }

    public GenreDB(int genrerID) {
        this.genrerID = genrerID;
    }

    public GenreDB() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getGenrerID() {
        return genrerID;
    }

    public void setGenrerID(int genrerID) {
        this.genrerID = genrerID;
    }

    public String getGenrerName() {
        return genrerName;
    }

    public void setGenrerName(String genrerName) {
        this.genrerName = genrerName;
    }
}
