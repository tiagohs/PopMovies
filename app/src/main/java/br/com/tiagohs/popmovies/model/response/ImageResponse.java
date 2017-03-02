package br.com.tiagohs.popmovies.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.util.enumerations.ArtworkType;

/**
 * Created by Tiago Henrique on 24/08/2016.
 */
public class ImageResponse implements Parcelable {

    @JsonProperty("id")
    private int id;

    @JsonProperty("backdrops")
    private List<Artwork> backdrops = Collections.emptyList();
    @JsonProperty("posters")
    private List<Artwork> posters = Collections.emptyList();
    @JsonProperty("profiles")
    private List<Artwork> profiles = Collections.emptyList();
    @JsonProperty("stills")
    private List<Artwork> stills = Collections.emptyList();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public List<Artwork> getBackdrops() {
        return backdrops;
    }

    public List<Artwork> getPosters() {
        return posters;
    }

    public List<Artwork> getProfiles() {
        return profiles;
    }

    public void setBackdrops(List<Artwork> backdrops) {
        this.backdrops = backdrops;
    }

    public void setPosters(List<Artwork> posters) {
        this.posters = posters;
    }

    public void setProfiles(List<Artwork> profiles) {
        this.profiles = profiles;
    }

    public void setStills(List<Artwork> stills) {
        this.stills = stills;
    }

    /**
     * Return a list of all the artwork with their types.
     *
     * Leaving the parameters blank will return all types
     *
     * @param artworkList
     * @return
     */
    public List<Artwork> getAll(ArtworkType... artworkList) {
        List<Artwork> artwork = new ArrayList<>();
        List<ArtworkType> types;

        if (artworkList.length > 0) {
            types = new ArrayList<>(Arrays.asList(artworkList));
        } else {
            types = new ArrayList<>(Arrays.asList(ArtworkType.values()));
        }

        // Add all the posters to the list
        if (types.contains(ArtworkType.POSTER)) {
            updateArtworkType(posters, ArtworkType.POSTER);
            artwork.addAll(posters);
        }

        // Add all the backdrops to the list
        if (types.contains(ArtworkType.BACKDROP)) {
            updateArtworkType(backdrops, ArtworkType.BACKDROP);
            artwork.addAll(backdrops);
        }

        // Add all the profiles to the list
        if (types.contains(ArtworkType.PROFILE)) {
            updateArtworkType(profiles, ArtworkType.PROFILE);
            artwork.addAll(profiles);
        }

        // Add all the stills to the list
        if (types.contains(ArtworkType.STILL)) {
            updateArtworkType(stills, ArtworkType.STILL);
            artwork.addAll(stills);
        }

        return artwork;
    }

    private void updateArtworkType(List<Artwork> artworkList, ArtworkType type) {
        for (Artwork artwork : artworkList) {
            artwork.setArtworkType(type);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeTypedList(this.backdrops);
        dest.writeTypedList(this.posters);
        dest.writeTypedList(this.profiles);
        dest.writeTypedList(this.stills);
    }

    public ImageResponse() {
    }

    protected ImageResponse(Parcel in) {
        this.id = in.readInt();
        this.backdrops = in.createTypedArrayList(Artwork.CREATOR);
        this.posters = in.createTypedArrayList(Artwork.CREATOR);
        this.profiles = in.createTypedArrayList(Artwork.CREATOR);
        this.stills = in.createTypedArrayList(Artwork.CREATOR);
    }

    public static final Parcelable.Creator<ImageResponse> CREATOR = new Parcelable.Creator<ImageResponse>() {
        @Override
        public ImageResponse createFromParcel(Parcel source) {
            return new ImageResponse(source);
        }

        @Override
        public ImageResponse[] newArray(int size) {
            return new ImageResponse[size];
        }
    };
}
