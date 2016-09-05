package br.com.tiagohs.popmovies.model.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.tiagohs.popmovies.util.enumerations.CreditType;

/**
 * Created by Tiago Henrique on 04/09/2016.
 */
public class CarrerMoviesDTO {
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
}
