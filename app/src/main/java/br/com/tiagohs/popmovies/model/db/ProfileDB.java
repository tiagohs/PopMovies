package br.com.tiagohs.popmovies.model.db;

import java.util.Calendar;
import java.util.List;

public class ProfileDB {
    private long profileID;
    private String descricao;
    private Calendar birthday;
    private String country;
    private String genrer;
    private long totalHorasAssistidas;

    private UserDB user;
    private List<MovieDB> filmes;
    private List<MovieDB> filmesAssistidos;
    private List<MovieDB> filmesQueroVer;
    private List<MovieDB> filmesNaoQueroVer;
    private List<MovieDB> filmesFavoritos;

    public ProfileDB() {

    }

    public List<MovieDB> getFilmes() {
        return filmes;
    }

    public void setFilmes(List<MovieDB> filmes) {
        this.filmes = filmes;
    }

    public long getProfileID() {
        return profileID;
    }

    public void setProfileID(long profileID) {
        this.profileID = profileID;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public long getTotalHorasAssistidas() {
        return totalHorasAssistidas;
    }

    public void setTotalHorasAssistidas(long totalHorasAssistidas) {
        this.totalHorasAssistidas = totalHorasAssistidas;
    }

    public UserDB getUser() {
        return user;
    }

    public void setUser(UserDB user) {
        this.user = user;
    }

    public List<MovieDB> getFilmesAssistidos() {
        return filmesAssistidos;
    }

    public void setFilmesAssistidos(List<MovieDB> filmesAssistidos) {
        this.filmesAssistidos = filmesAssistidos;
    }

    public List<MovieDB> getFilmesQueroVer() {
        return filmesQueroVer;
    }

    public void setFilmesQueroVer(List<MovieDB> filmesQueroVer) {
        this.filmesQueroVer = filmesQueroVer;
    }

    public List<MovieDB> getFilmesFavoritos() {
        return filmesFavoritos;
    }

    public void setFilmesFavoritos(List<MovieDB> filmesFavoritos) {
        this.filmesFavoritos = filmesFavoritos;
    }

    public List<MovieDB> getFilmesNaoQueroVer() {
        return filmesNaoQueroVer;
    }

    public void setFilmesNaoQueroVer(List<MovieDB> filmesNaoQueroVer) {
        this.filmesNaoQueroVer = filmesNaoQueroVer;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGenrer() {
        return genrer;
    }

    public void setGenrer(String genrer) {
        this.genrer = genrer;
    }
}
