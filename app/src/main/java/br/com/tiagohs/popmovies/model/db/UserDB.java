package br.com.tiagohs.popmovies.model.db;

public class UserDB {
    private int userID;
    private String nome;
    private String email;
    private String senha;
    private boolean isLogged;
    private long profileID;

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

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    public long getProfileID() {
        return profileID;
    }

    public void setProfileID(long profileID) {
        this.profileID = profileID;
    }
}
