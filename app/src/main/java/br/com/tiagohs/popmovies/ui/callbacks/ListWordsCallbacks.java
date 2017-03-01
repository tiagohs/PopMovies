package br.com.tiagohs.popmovies.ui.callbacks;

import br.com.tiagohs.popmovies.model.dto.ItemListDTO;
import br.com.tiagohs.popmovies.util.enumerations.ItemType;

/**
 * Created by Tiago Henrique on 04/09/2016.
 */
public interface ListWordsCallbacks {
    void onItemSelected(ItemListDTO item, ItemType itemType);
}
