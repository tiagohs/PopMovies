package br.com.tiagohs.popmovies.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tiago Henrique on 04/09/2016.
 */
public class PersonListDTO implements Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mID);
        dest.writeString(this.mProfilePath);
        dest.writeString(this.mNamePerson);
        dest.writeString(this.mSubtitulo);
    }

    protected PersonListDTO(Parcel in) {
        this.mID = in.readInt();
        this.mProfilePath = in.readString();
        this.mNamePerson = in.readString();
        this.mSubtitulo = in.readString();
    }

    public static final Parcelable.Creator<PersonListDTO> CREATOR = new Parcelable.Creator<PersonListDTO>() {
        @Override
        public PersonListDTO createFromParcel(Parcel source) {
            return new PersonListDTO(source);
        }

        @Override
        public PersonListDTO[] newArray(int size) {
            return new PersonListDTO[size];
        }
    };
}
