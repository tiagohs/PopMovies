package br.com.tiagohs.popmovies.ui.contracts;

import java.util.List;

import br.com.tiagohs.popmovies.model.dto.PersonListDTO;
import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.model.response.GenericListResponse;
import br.com.tiagohs.popmovies.ui.presenter.IPresenter;
import br.com.tiagohs.popmovies.ui.view.IView;
import io.reactivex.Observable;

/**
 * Created by Tiago on 25/02/2017.
 */

public class ListPersonsDefaultContract {

    public interface ListPersonsDefaultInterceptor {

        Observable<GenericListResponse<PersonFind>> getPersons(int currentPage);

    }

    public interface ListPersonsDefaultPresenter extends IPresenter<ListPersonsDefaultView> {

        void getPersons();
    }

    public interface ListPersonsDefaultView extends IView {

        void setProgressVisibility(int visibityState);
        void setRecyclerViewVisibility(int visibilityState);

        void setupRecyclerView();
        void setListMovies(List<PersonListDTO> listPersons, boolean hasMorePages);
        void addAllMovies(List<PersonListDTO> listPersons, boolean hasMorePages);
        void updateAdapter();
    }
}
