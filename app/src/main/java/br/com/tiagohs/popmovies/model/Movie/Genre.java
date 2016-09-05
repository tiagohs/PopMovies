package br.com.tiagohs.popmovies.model.movie;

import java.io.Serializable;

import br.com.tiagohs.popmovies.model.IDNameAbstract;

public class Genre extends IDNameAbstract implements Serializable {

    private int imgPath;

    public Genre() {

    }

    public Genre(int id, String name) {
        super(id, name);
    }


    public int getImgPath() {
        return imgPath;
    }

    public void setImgPath(int imgPath) {
        this.imgPath = imgPath;
    }
}
