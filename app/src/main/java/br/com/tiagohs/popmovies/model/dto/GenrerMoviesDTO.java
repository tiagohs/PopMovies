package br.com.tiagohs.popmovies.model.dto;

/**
 * Created by Tiago on 20/01/2017.
 */

public class GenrerMoviesDTO {
    private int mGenrerID;
    private int mGenrerName;
    private Long mTotalMovies;

    public GenrerMoviesDTO() {
    }

    public GenrerMoviesDTO(int genrerID, int genrerName, Long totalMovies) {
        mGenrerID = genrerID;
        mGenrerName = genrerName;
        mTotalMovies = totalMovies;
    }

    public int getGenrerID() {
        return mGenrerID;
    }

    public void setGenrerID(int genrerID) {
        mGenrerID = genrerID;
    }

    public int getGenrerName() {
        return mGenrerName;
    }

    public void setGenrerName(int genrerName) {
        mGenrerName = genrerName;
    }

    public Long getTotalMovies() {
        return mTotalMovies;
    }

    public void setTotalMovies(Long totalMovies) {
        mTotalMovies = totalMovies;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof GenrerMoviesDTO) {
            GenrerMoviesDTO g = (GenrerMoviesDTO) obj;
            return g.getGenrerID() == getGenrerID();
        }

        return false;
    }
}
