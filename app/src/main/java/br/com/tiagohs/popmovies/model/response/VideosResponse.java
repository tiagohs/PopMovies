package br.com.tiagohs.popmovies.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

import br.com.tiagohs.popmovies.model.media.Video;

/**
 * Created by Tiago Henrique on 24/08/2016.
 */
public class VideosResponse {

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
}
