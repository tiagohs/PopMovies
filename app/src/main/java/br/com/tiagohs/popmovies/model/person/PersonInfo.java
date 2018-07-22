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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import br.com.tiagohs.popmovies.model.ChangeKeyItem;
import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.model.atwork.ArtworkMedia;
import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;
import br.com.tiagohs.popmovies.model.credits.CreditTVBasic;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.response.ChangesResponse;
import br.com.tiagohs.popmovies.model.response.CombinedCreditsResponse;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.util.enumerations.Gender;
import br.com.tiagohs.popmovies.util.enumerations.PeopleMethod;

/**
 * @author stuart.boston
 */
public class PersonInfo extends PersonBasic {

    private static final long serialVersionUID = 100L;

    private Date birthdayDate;

    @JsonProperty("adult")
    private boolean adult;
    @JsonProperty("also_known_as")
    private List<String> alsoKnownAs;
    @JsonProperty("biography")
    private String biography;
    @JsonProperty("birthday")
    private String birthday;
    @JsonProperty("known_for_department")
    private String knownForDepartment;
    @JsonProperty("deathday")
    private String deathday;
    @JsonProperty("homepage")
    private String homepage;
    @JsonProperty("imdb_id")
    private String imdbId;
    @JsonProperty("place_of_birth")
    private String placeOfBirth;
    @JsonProperty("popularity")
    private float popularity;
    private Gender gender;
    private Set<PeopleMethod> methods = EnumSet.noneOf(PeopleMethod.class);


    private List<MovieListDTO> moviesCarrer = Collections.emptyList();

    // AppendToResponse Properties
    private List<ChangeKeyItem> changes = Collections.emptyList();
    private ExternalID externalIDs = new ExternalID();
    private List<Artwork> images = Collections.emptyList();
    private List<ArtworkMedia> taggedImages = Collections.emptyList();

    private PersonCreditList<CreditMovieBasic> movieCredits = new PersonCreditList<>();
    private PersonCreditList<CreditTVBasic> tvCredits = new PersonCreditList<>();

    private List<CreditMovieBasic> castCombined = Collections.emptyList();
    private List<CreditMovieBasic> crewCombined = Collections.emptyList();

    private List<CreditMovieBasic> movieCombinedCredits = Collections.emptyList();
    private List<CreditMovieBasic> tvCombinedCredits = Collections.emptyList();

    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public List<String> getAlsoKnownAs() {
        return alsoKnownAs;
    }

    public void setAlsoKnownAs(List<String> alsoKnownAs) {
        this.alsoKnownAs = alsoKnownAs;

    }

    public String getKnownForDepartment() {
        return knownForDepartment;
    }

    public void setKnownForDepartment(String knownForDepartment) {
        this.knownForDepartment = knownForDepartment;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeathday() {
        return deathday;
    }

    public void setDeathday(String deathday) {
        this.deathday = deathday;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }
    //</editor-fold>

    private void addMethod(PeopleMethod method) {
        methods.add(method);
    }

    public int getYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate());
        return calendar.get(Calendar.YEAR);
    }

    public int getDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate());
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate());
        return calendar.get(Calendar.MONTH);
    }

    private Date getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            return sdf.parse(birthday);
        } catch (ParseException e) {
            return new Date();
        } catch (NullPointerException e) {
            return new Date();
        }
    }

    public List<MovieListDTO> getMoviesCarrer() {
        return moviesCarrer;
    }

    public void setMoviesCarrer(List<MovieListDTO> moviesCarrer) {
        this.moviesCarrer = moviesCarrer;
    }

    public Gender getGender() {
        return gender;
    }

    @JsonSetter("gender")
    public void setGender(int gender) {
        this.gender = Gender.fromInteger(gender);
    }

    public boolean hasMethod(PeopleMethod method) {
        return methods.contains(method);
    }

    //<editor-fold defaultstate="collapsed" desc="AppendToResponse Setters">
    @JsonSetter("changes")
    public void setChanges(ChangesResponse changes) {
        this.changes = changes.getChangedItems();
        addMethod(PeopleMethod.CHANGES);
    }

    @JsonSetter("external_ids")
    public void setExternalIDs(ExternalID externalIDs) {
        this.externalIDs = externalIDs;
        addMethod(PeopleMethod.EXTERNAL_IDS);
    }

    @JsonSetter("images")
    public void setImages(ImageResponse images) {
        this.images = images.getAll();
        addMethod(PeopleMethod.IMAGES);
    }

    @JsonSetter("movie_credits")
    public void setMovieCredits(PersonCreditList<CreditMovieBasic> movieCredits) {
        this.movieCredits = movieCredits;
        addMethod(PeopleMethod.MOVIE_CREDITS);
        createAreasAtuacoesPerson(movieCredits);
    }

    public void createAreasAtuacoesPerson(PersonCreditList<CreditMovieBasic> movieCredits) {
        List<CreditMovieBasic> movies = movieCredits.getCast();
        movies.addAll(movieCredits.getCrew());

        if (getMovieCredits().getCast().size() > 0)
            areasAtuacao.add(getGender() == Gender.FEMALE ? "Actress" : "Actor");

        for (CreditMovieBasic movie : movies) {
            if (!areasAtuacao.contains(movie.getDepartment()) && movie.getDepartment() != null) {
                if (!areasAtuacao.contains(movie.getDepartment()))
                    areasAtuacao.add(movie.getDepartment());
            }
        }
    }

    @JsonSetter("tagged_images")
    public void setTaggedImages(GenericListResponse<ArtworkMedia> taggedImages) {
        this.taggedImages = taggedImages.getResults();
        addMethod(PeopleMethod.TAGGED_IMAGES);
    }

    @JsonSetter("tv_credits")
    public void setTvCredits(PersonCreditList<CreditTVBasic> tvCredits) {
        this.tvCredits = tvCredits;
        addMethod(PeopleMethod.TV_CREDITS);
    }

    @JsonSetter("combined_credits")
    public void  setCombinedCredits(CombinedCreditsResponse combinedCredits) {
        this.castCombined = combinedCredits.getCast();
        this.crewCombined = combinedCredits.getCrew();

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="AppendToResponse Getters">
    public List<ChangeKeyItem> getChanges() {
        return changes;
    }

    public ExternalID getExternalIDs() {
        return externalIDs;
    }

    public List<Artwork> getImages() {
        return images;
    }

    public PersonCreditList<CreditMovieBasic> getMovieCredits() {
        return movieCredits;
    }

    public List<ArtworkMedia> getTaggedImages() {
        return taggedImages;
    }

    public PersonCreditList<CreditTVBasic> getTvCredits() {
        return tvCredits;
    }

    public List<CreditMovieBasic> getCastCombined() {
        return castCombined;
    }

    public List<CreditMovieBasic> getCrewCombined() {
        return crewCombined;
    }

    //</editor-fold>


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.birthdayDate != null ? this.birthdayDate.getTime() : -1);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeStringList(this.alsoKnownAs);
        dest.writeString(this.biography);
        dest.writeString(this.birthday);
        dest.writeString(this.deathday);
        dest.writeString(this.homepage);
        dest.writeString(this.imdbId);
        dest.writeString(this.placeOfBirth);
        dest.writeFloat(this.popularity);
        dest.writeInt(this.gender == null ? -1 : this.gender.ordinal());
        dest.writeValue(this.methods);
        dest.writeTypedList(this.moviesCarrer);
        dest.writeList(this.changes);
        dest.writeParcelable(this.externalIDs, flags);
        dest.writeTypedList(this.images);
        dest.writeTypedList(this.taggedImages);
        dest.writeParcelable(this.movieCredits, flags);
        dest.writeParcelable(this.tvCredits, flags);
        dest.writeTypedList(this.castCombined);
        dest.writeTypedList(this.crewCombined);
        dest.writeTypedList(this.movieCombinedCredits);
        dest.writeTypedList(this.tvCombinedCredits);
    }

    public PersonInfo() {
    }

    protected PersonInfo(Parcel in) {
        super(in);
        long tmpBirthdayDate = in.readLong();
        this.birthdayDate = tmpBirthdayDate == -1 ? null : new Date(tmpBirthdayDate);
        this.adult = in.readByte() != 0;
        this.alsoKnownAs = in.createStringArrayList();
        this.biography = in.readString();
        this.birthday = in.readString();
        this.deathday = in.readString();
        this.homepage = in.readString();
        this.imdbId = in.readString();
        this.placeOfBirth = in.readString();
        this.popularity = in.readFloat();
        int tmpGender = in.readInt();
        this.gender = tmpGender == -1 ? null : Gender.values()[tmpGender];
        this.methods = in.readParcelable(Set.class.getClassLoader());
        this.moviesCarrer = in.createTypedArrayList(MovieListDTO.CREATOR);
        this.changes = new ArrayList<ChangeKeyItem>();
        in.readList(this.changes, ChangeKeyItem.class.getClassLoader());
        this.externalIDs = in.readParcelable(ExternalID.class.getClassLoader());
        this.images = in.createTypedArrayList(Artwork.CREATOR);
        this.taggedImages = in.createTypedArrayList(ArtworkMedia.CREATOR);
        this.movieCredits = in.readParcelable(PersonCreditList.class.getClassLoader());
        this.tvCredits = in.readParcelable(PersonCreditList.class.getClassLoader());
        this.castCombined = (ArrayList) in.createTypedArrayList(CreditMovieBasic.CREATOR);
        this.crewCombined = (ArrayList) in.createTypedArrayList(CreditMovieBasic.CREATOR);
        this.movieCombinedCredits = (ArrayList) in.createTypedArrayList(CreditMovieBasic.CREATOR);
        this.tvCombinedCredits = (ArrayList) in.createTypedArrayList(CreditMovieBasic.CREATOR);
    }

    public static final Creator<PersonInfo> CREATOR = new Creator<PersonInfo>() {
        @Override
        public PersonInfo createFromParcel(Parcel source) {
            return new PersonInfo(source);
        }

        @Override
        public PersonInfo[] newArray(int size) {
            return new PersonInfo[size];
        }
    };
}
