/*
 *      Copyright (c) 2004-2016 Stuart Boston
 *
 *      This file is part of TheMovieDB API.
 *
 *      TheMovieDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TheMovieDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheMovieDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package br.com.tiagohs.popmovies.model.person;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.model.IDNameAbstract;

/**
 * @author stuart.boston
 */
public class PersonBasic extends IDNameAbstract implements Parcelable {

    private static final long serialVersionUID = 100L;

    @JsonProperty("profile_path")
    private String profilePath;

    protected List<String> areasAtuacao;

    public PersonBasic() {
        areasAtuacao = new ArrayList<>();
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public List<String> getAreasAtuacao() {
        return areasAtuacao;
    }

    public void setAreasAtuacao(List<String> areasAtuacao) {
        this.areasAtuacao = areasAtuacao;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.profilePath);
        dest.writeStringList(this.areasAtuacao);
    }

    protected PersonBasic(Parcel in) {
        this.profilePath = in.readString();
        this.areasAtuacao = in.createStringArrayList();
    }

    public static final Creator<PersonBasic> CREATOR = new Creator<PersonBasic>() {
        @Override
        public PersonBasic createFromParcel(Parcel source) {
            return new PersonBasic(source);
        }

        @Override
        public PersonBasic[] newArray(int size) {
            return new PersonBasic[size];
        }
    };
}
