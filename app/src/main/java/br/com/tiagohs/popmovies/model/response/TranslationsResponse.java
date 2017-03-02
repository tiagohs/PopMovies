package br.com.tiagohs.popmovies.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Translation;

/**
 * Created by Tiago Henrique on 24/08/2016.
 */
public class TranslationsResponse implements Parcelable {

    @JsonProperty("id")
    private int id;

    @JsonProperty("translations")
    private List<Translation> translations;

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeTypedList(this.translations);
    }

    public TranslationsResponse() {
    }

    protected TranslationsResponse(Parcel in) {
        this.id = in.readInt();
        this.translations = in.createTypedArrayList(Translation.CREATOR);
    }

    public static final Parcelable.Creator<TranslationsResponse> CREATOR = new Parcelable.Creator<TranslationsResponse>() {
        @Override
        public TranslationsResponse createFromParcel(Parcel source) {
            return new TranslationsResponse(source);
        }

        @Override
        public TranslationsResponse[] newArray(int size) {
            return new TranslationsResponse[size];
        }
    };
}
