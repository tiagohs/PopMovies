package br.com.tiagohs.popmovies.model.db;

import android.os.Parcel;
import android.os.Parcelable;

public class UserDB implements Parcelable {
    public static final int LOGIN_FACEBOOK = 0;
    public static final int LOGIN_TWITTER = 1;

    public static final int PHOTO_LOCAL = 0;
    public static final int PHOTO_ONLINE = 1;

    private int userID;
    private String nome;
    private String username;
    private String email;
    private String picturePath;
    private String token;
    private int typePhoto;
    private int typeLogin;
    private boolean isLogged;
    private long profileID;
    private String mLocalPicture;

    public UserDB() {
    }

    public UserDB(int userID, String nome, String username, String email, String picturePath, String token, int typePhoto, int typeLogin, boolean isLogged, String localPicture) {
        this.userID = userID;
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.picturePath = picturePath;
        this.token = token;
        this.typePhoto = typePhoto;
        this.typeLogin = typeLogin;
        this.isLogged = isLogged;
        mLocalPicture = localPicture;
    }

    public UserDB(String nome, String username, String email, String picturePath, String token, int typePhoto, int typeLogin, boolean isLogged) {
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.picturePath = picturePath;
        this.token = token;
        this.typePhoto = typePhoto;
        this.typeLogin = typeLogin;
        this.isLogged = isLogged;
    }

    public long getProfileID() {
        return profileID;
    }

    public void setProfileID(long profileID) {
        this.profileID = profileID;
    }

    public int getTypePhoto() {
        return typePhoto;
    }

    public void setTypePhoto(int typePhoto) {
        this.typePhoto = typePhoto;
    }

    public String getLocalPicture() {
        return mLocalPicture;
    }

    public void setLocalPicture(String localPicture) {
        mLocalPicture = localPicture;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTypeLogin() {
        return typeLogin;
    }

    public void setTypeLogin(int typeLogin) {
        this.typeLogin = typeLogin;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userID);
        dest.writeString(this.nome);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.picturePath);
        dest.writeString(this.token);
        dest.writeInt(this.typePhoto);
        dest.writeInt(this.typeLogin);
        dest.writeByte(this.isLogged ? (byte) 1 : (byte) 0);
        dest.writeLong(this.profileID);
        dest.writeString(this.mLocalPicture);
    }

    protected UserDB(Parcel in) {
        this.userID = in.readInt();
        this.nome = in.readString();
        this.username = in.readString();
        this.email = in.readString();
        this.picturePath = in.readString();
        this.token = in.readString();
        this.typePhoto = in.readInt();
        this.typeLogin = in.readInt();
        this.isLogged = in.readByte() != 0;
        this.profileID = in.readLong();
        this.mLocalPicture = in.readString();
    }

    public static final Parcelable.Creator<UserDB> CREATOR = new Parcelable.Creator<UserDB>() {
        @Override
        public UserDB createFromParcel(Parcel source) {
            return new UserDB(source);
        }

        @Override
        public UserDB[] newArray(int size) {
            return new UserDB[size];
        }
    };
}
