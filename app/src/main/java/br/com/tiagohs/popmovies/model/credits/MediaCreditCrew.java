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
package br.com.tiagohs.popmovies.model.credits;

import android.os.Parcel;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Stuart.Boston
 */
public class MediaCreditCrew extends MediaCredit {

    private static final long serialVersionUID = 100L;

    @JsonProperty("department")
    private String department;
    @JsonProperty("job")
    private String job;
    @JsonProperty("gender")
    private String gender;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.department);
        dest.writeString(this.job);
    }

    public MediaCreditCrew() {
    }

    protected MediaCreditCrew(Parcel in) {
        super(in);
        this.department = in.readString();
        this.job = in.readString();
    }

    public static final Creator<MediaCreditCrew> CREATOR = new Creator<MediaCreditCrew>() {
        @Override
        public MediaCreditCrew createFromParcel(Parcel source) {
            return new MediaCreditCrew(source);
        }

        @Override
        public MediaCreditCrew[] newArray(int size) {
            return new MediaCreditCrew[size];
        }
    };
}
