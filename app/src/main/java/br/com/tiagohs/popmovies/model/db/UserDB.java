package br.com.tiagohs.popmovies.model.db;

import android.graphics.Bitmap;

public class UserDB {
    public static final int LOGIN_FACEBOOK = 0;
    public static final int LOGIN_TWITTER = 1;

    public static final int PHOTO_LOCAL = 0;
    public static final int PHOTO_ONLINE = 1;

    private int userID;
    private String nome;
    private String username;
    private String email;
    private String senha;
    private String picturePath;
    private String token;
    private int typePhoto;
    private int typeLogin;
    private boolean isLogged;
    private String mLocalPicture;

    public UserDB() {
    }

    public UserDB(int userID, String nome, String username, String email, String senha, String picturePath, String token, int typePhoto, int typeLogin, boolean isLogged, String localPicture) {
        this.userID = userID;
        this.nome = nome;
        this.username = username;
        this.email = email;
        this.senha = senha;
        this.picturePath = picturePath;
        this.token = token;
        this.typePhoto = typePhoto;
        this.typeLogin = typeLogin;
        this.isLogged = isLogged;
        mLocalPicture = localPicture;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
}
