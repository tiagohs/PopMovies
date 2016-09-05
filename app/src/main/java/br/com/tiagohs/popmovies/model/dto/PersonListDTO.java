package br.com.tiagohs.popmovies.model.dto;

/**
 * Created by Tiago Henrique on 04/09/2016.
 */
public class PersonListDTO {
    private int mID;
    private String mProfilePath;
    private String mNamePerson;
    private String mSubtitulo;

    public PersonListDTO() {
    }

    public PersonListDTO(int ID, String profilePath, String namePerson, String subtitulo) {
        mID = ID;
        mProfilePath = profilePath;
        mNamePerson = namePerson;
        mSubtitulo = subtitulo;
    }

    public int getID() {
        return mID;
    }

    public void setID(int ID) {
        mID = ID;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
    }

    public String getNamePerson() {
        return mNamePerson;
    }

    public void setNamePerson(String namePerson) {
        mNamePerson = namePerson;
    }

    public String getSubtitulo() {
        return mSubtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        mSubtitulo = subtitulo;
    }
}
