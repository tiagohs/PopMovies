package br.com.tiagohs.popmovies.model.dto;

import java.io.Serializable;

/**
 * Created by Tiago Henrique on 04/09/2016.
 */
public class ImageDTO implements Serializable {
    private int movieID;
    private String imageID;
    private String imagePath;

    public ImageDTO(int movieID, String imageID, String imagePath) {
        this.movieID = movieID;
        this.imageID = imageID;
        this.imagePath = imagePath;
    }



    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof ImageDTO) {
            ImageDTO im = (ImageDTO) obj;
            return this.getImagePath().equals(im.getImagePath());
        }
        return false;
    }
}
