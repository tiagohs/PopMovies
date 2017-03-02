package br.com.tiagohs.popmovies.model.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.tiagohs.popmovies.util.enumerations.CreditType;

public class CarrerMoviesDTO implements Parcelable {
    private int id;
    private String mTitle;
    private String mOriginalTitle;
    private String mPosterPath;
    private String mReleaseDate;
    private String mCharacter;
    private String mDepartment;
    private CreditType mCreditType;

    public CarrerMoviesDTO() {
    }

    public CarrerMoviesDTO(int id) {
        this.id = id;
    }

    public CarrerMoviesDTO(int id, String title, String originalTitle, String posterPath, String releaseDate, String character, String department, CreditType creditType) {
        this.id = id;
        mTitle = title;
        mOriginalTitle = originalTitle;
        mPosterPath = posterPath;
        mReleaseDate = releaseDate;
        mCharacter = character;
        mDepartment = department;
        mCreditType = creditType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public void setCharacter(String character) {
        mCharacter = character;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        mDepartment = department;
    }

    public CreditType getCreditType() {
        return mCreditType;
    }

    public void setCreditType(CreditType creditType) {
        mCreditType = creditType;
    }

    public Date getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;

        try {
            return date = sdf.parse(mReleaseDate);
        } catch (ParseException e) {
            return new Date();
        } catch (NullPointerException e) {
            return new Date();
        }
    }

    public int getYearRelease() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDate());
        return calendar.get(Calendar.YEAR);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CarrerMoviesDTO) {
            CarrerMoviesDTO carrerMoviesDTO = (CarrerMoviesDTO) obj;
            return getId() == carrerMoviesDTO.getId();
        }

        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.mTitle);
        dest.writeString(this.mOriginalTitle);
        dest.writeString(this.mPosterPath);
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.mCharacter);
        dest.writeString(this.mDepartment);
        dest.writeInt(this.mCreditType == null ? -1 : this.mCreditType.ordinal());
    }

    protected CarrerMoviesDTO(Parcel in) {
        this.id = in.readInt();
        this.mTitle = in.readString();
        this.mOriginalTitle = in.readString();
        this.mPosterPath = in.readString();
        this.mReleaseDate = in.readString();
        this.mCharacter = in.readString();
        this.mDepartment = in.readString();
        int tmpMCreditType = in.readInt();
        this.mCreditType = tmpMCreditType == -1 ? null : CreditType.values()[tmpMCreditType];
    }

    public static final Parcelable.Creator<CarrerMoviesDTO> CREATOR = new Parcelable.Creator<CarrerMoviesDTO>() {
        @Override
        public CarrerMoviesDTO createFromParcel(Parcel source) {
            return new CarrerMoviesDTO(source);
        }

        @Override
        public CarrerMoviesDTO[] newArray(int size) {
            return new CarrerMoviesDTO[size];
        }
    };
}
