package br.com.tiagohs.popmovies.ui.callbacks;

import java.util.List;

import br.com.tiagohs.popmovies.model.dto.ImageDTO;

/**
 * Created by Tiago Henrique on 05/09/2016.
 */
public interface ImagesCallbacks {

    void onClickImage(List<ImageDTO> imagens, ImageDTO imageDTO);
}
