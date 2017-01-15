package br.com.tiagohs.popmovies.view;

import android.widget.ImageView;

import java.util.List;

import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.model.person.PersonFind;

public interface SearchPersonsView extends BaseView  {

    void setListPersons(List<PersonFind> listPersons, boolean hasMorePages);
    void addAllPersons(List<PersonFind> listPersons, boolean hasMorePages);

    void setNenhumaPessoaEncontradoVisibility(int visibility);
    void setupRecyclerView();
    void updateAdapter();
}
