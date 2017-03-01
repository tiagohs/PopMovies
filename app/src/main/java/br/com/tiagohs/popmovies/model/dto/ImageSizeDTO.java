package br.com.tiagohs.popmovies.model.dto;

/**
 * Created by Tiago on 28/02/2017.
 */

public class ImageSizeDTO {

    private String sizeName;
    private String size;

    public ImageSizeDTO(String sizeName, String size) {
        this.sizeName = sizeName;
        this.size = size;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return getSizeName();
    }
}
