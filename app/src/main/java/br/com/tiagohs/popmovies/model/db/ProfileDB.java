package br.com.tiagohs.popmovies.model.db;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.List;

public class ProfileDB implements Parcelable {
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

    public ProfileDB(UserDB user, String country) {
        this.user = user;
        this.country = country;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.profileID);
        dest.writeString(this.descricao);
        dest.writeSerializable(this.birthday);
        dest.writeString(this.country);
        dest.writeString(this.genrer);
        dest.writeLong(this.totalHorasAssistidas);
        dest.writeParcelable(this.user, flags);
        dest.writeTypedList(this.filmes);
        dest.writeTypedList(this.filmesAssistidos);
        dest.writeTypedList(this.filmesQueroVer);
        dest.writeTypedList(this.filmesNaoQueroVer);
        dest.writeTypedList(this.filmesFavoritos);
    }

    protected ProfileDB(Parcel in) {
        this.profileID = in.readLong();
        this.descricao = in.readString();
        this.birthday = (Calendar) in.readSerializable();
        this.country = in.readString();
        this.genrer = in.readString();
        this.totalHorasAssistidas = in.readLong();
        this.user = in.readParcelable(UserDB.class.getClassLoader());
        this.filmes = in.createTypedArrayList(MovieDB.CREATOR);
        this.filmesAssistidos = in.createTypedArrayList(MovieDB.CREATOR);
        this.filmesQueroVer = in.createTypedArrayList(MovieDB.CREATOR);
        this.filmesNaoQueroVer = in.createTypedArrayList(MovieDB.CREATOR);
        this.filmesFavoritos = in.createTypedArrayList(MovieDB.CREATOR);
    }

    public static final Parcelable.Creator<ProfileDB> CREATOR = new Parcelable.Creator<ProfileDB>() {
        @Override
        public ProfileDB createFromParcel(Parcel source) {
            return new ProfileDB(source);
        }

        @Override
        public ProfileDB[] newArray(int size) {
            return new ProfileDB[size];
        }
    };
}
