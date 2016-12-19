package br.com.tiagohs.popmovies.model.db;

import java.util.List;

public class ProfileDB {
    private long profileID;
    private String descricao;
    private String fotoPath;
    private int totalHorasAssistidas;

    private UserDB user;
    private List<MovieDB> filmesAssistidos;
    private List<MovieDB> filmesFavoritos;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }

    public int getTotalHorasAssistidas() {
        return totalHorasAssistidas;
    }

    public void setTotalHorasAssistidas(int totalHorasAssistidas) {
        this.totalHorasAssistidas = totalHorasAssistidas;
    }

    public List<MovieDB> getFilmesAssistidos() {
        return filmesAssistidos;
    }

    public void setFilmesAssistidos(List<MovieDB> filmesAssistidos) {
        this.filmesAssistidos = filmesAssistidos;
    }

    public long getProfileID() {
        return profileID;
    }

    public void setProfileID(long profileID) {
        this.profileID = profileID;
    }

    public UserDB getUser() {
        return user;
    }

    public void setUser(UserDB user) {
        this.user = user;
    }

    public List<MovieDB> getFilmesFavoritos() {
        return filmesFavoritos;
    }

    public void setFilmesFavoritos(List<MovieDB> filmesFavoritos) {
        this.filmesFavoritos = filmesFavoritos;
    }

    private void calcularHorasAssistidas() {
        for (MovieDB movie : filmesAssistidos) {
            totalHorasAssistidas += movie.getDuracao();
        }
    }
}
