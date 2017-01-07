package br.com.tiagohs.popmovies.model.db;

import java.util.List;

public class ProfileDB {
    private long profileID;
    private String descricao;
    private long totalHorasAssistidas;

    private UserDB user;
    private List<MovieDB> filmesAssistidos;
    private List<MovieDB> filmesQueroVer;
    private List<MovieDB> filmesFavoritos;

    public ProfileDB() {
    }

    public ProfileDB(long profileID, String descricao, long totalHorasAssistidas, UserDB user, List<MovieDB> filmesAssistidos, List<MovieDB> filmesQueroVer, List<MovieDB> filmesFavoritos) {
        this.profileID = profileID;
        this.descricao = descricao;
        this.totalHorasAssistidas = totalHorasAssistidas;
        this.user = user;
        this.filmesAssistidos = filmesAssistidos;
        this.filmesQueroVer = filmesQueroVer;
        this.filmesFavoritos = filmesFavoritos;
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
}
