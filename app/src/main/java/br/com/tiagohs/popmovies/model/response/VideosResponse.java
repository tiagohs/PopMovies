package br.com.tiagohs.popmovies.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Video;

public class VideosResponse implements Parcelable {

    @JsonProperty("id")
    private int id;

    private List<Video> videos = null;

    public List<Video> getVideos() {
        return videos;
    }

    @JsonSetter("results")
    public void setVideos(List<Video> videos) {
        this.videos = videos;
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
        dest.writeTypedList(this.videos);
    }

    public VideosResponse() {
    }

    protected VideosResponse(Parcel in) {
        this.id = in.readInt();
        this.videos = in.createTypedArrayList(Video.CREATOR);
    }

    public static final Parcelable.Creator<VideosResponse> CREATOR = new Parcelable.Creator<VideosResponse>() {
        @Override
        public VideosResponse createFromParcel(Parcel source) {
            return new VideosResponse(source);
        }

        @Override
        public VideosResponse[] newArray(int size) {
            return new VideosResponse[size];
        }
    };
}
