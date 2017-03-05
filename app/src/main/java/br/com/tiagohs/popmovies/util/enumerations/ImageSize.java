package br.com.tiagohs.popmovies.util.enumerations;

/**
 * Created by Tiago Henrique on 21/08/2016.
 */
public enum ImageSize {
    BACKDROP_300("w300"), BACKDROP_780("w780"), BACKDROP_1280("w1280"), BACKDROP_ORIGINAL("original"),
    LOGO_45("w45"), LOGO_92("w92"), LOGO_154("w154"), LOGO_185("w185"), LOGO_300("w300"), LOGO_500("w500"), LOGO_ORIGINAL("original"),
    POSTER_92("w92"), POSTER_154("w154"), POSTER_185("w185"), POSTER_342("w342"), POSTER_500("w500"), POSTER_780("w780"), POSTER_ORIGINAL("original"),
    PROFILE_45("w45"), PROFILE_185("w185"), PROFILE_632("h632"), PROFILE_ORIGINAL("original"),
    STIL_92("w92"), STIL_185("w185"), STIL_300("w300"), STIL_ORIGINAL("original");

    private String size;

    ImageSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
