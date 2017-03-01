package br.com.tiagohs.popmovies.ui.contracts;

import java.util.Calendar;
import java.util.Locale;

import br.com.tiagohs.popmovies.model.dto.DiscoverDTO;
import br.com.tiagohs.popmovies.ui.presenter.IPresenter;
import br.com.tiagohs.popmovies.ui.view.IView;

public class LancamentosSemanaContract {

    public interface LancamentosSemanaInterceptor {

    }

    public interface LancamentosSemanaPresenter extends IPresenter<LancamentosSemanaView> {

        void initUpdateMovies(Locale locale);
        void updateWithCalendar(Calendar baseDate, Locale locale);
        void onClickNext(Locale locale);
        void onClickAnterior(Locale locale);
    }

    public interface LancamentosSemanaView extends IView {

        void updateListMovies(DiscoverDTO discoverDTO);
        void updateDateText(String dateMin, String dateMax);
    }
}
