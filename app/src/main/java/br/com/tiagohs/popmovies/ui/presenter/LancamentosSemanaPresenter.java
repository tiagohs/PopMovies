package br.com.tiagohs.popmovies.ui.presenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import br.com.tiagohs.popmovies.ui.contracts.LancamentosSemanaContract;
import br.com.tiagohs.popmovies.model.dto.DiscoverDTO;
import br.com.tiagohs.popmovies.util.LocaleUtils;

public class LancamentosSemanaPresenter extends BasePresenter<LancamentosSemanaContract.LancamentosSemanaView, LancamentosSemanaContract.LancamentosSemanaInterceptor> implements LancamentosSemanaContract.LancamentosSemanaPresenter {
    private static final int DAYS_OF_WEEK = 7;

    private Calendar mAtualMinDate;
    private Calendar mAtualMaxDate;

    private SimpleDateFormat formatter;
    private SimpleDateFormat formatterDateView;

    private DiscoverDTO mDiscoverDTO;

    public LancamentosSemanaPresenter() {
        mAtualMinDate = Calendar.getInstance();
        mAtualMaxDate = Calendar.getInstance();

        formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatterDateView = new SimpleDateFormat("dd MMM yyyy");
    }

    public void updateWithCalendar(Calendar baseDate, Locale locale) {
        mAtualMaxDate.setTime(baseDate.getTime());
        mAtualMinDate.setTime(baseDate.getTime());

        initUpdateMovies(locale);
    }

    public void initUpdateMovies(Locale locale) {
        setDateByDayOfWeek(mAtualMinDate, Calendar.SUNDAY);
        setDateByDayOfWeek(mAtualMaxDate, Calendar.SATURDAY);

        updateValues(locale);
    }

    private void updateValues(Locale locale) {
        setParamenters(locale);

        mView.updateListMovies(mDiscoverDTO);
        mView.updateDateText(formatterDateView.format(mAtualMinDate.getTime()), formatterDateView.format(mAtualMaxDate.getTime()));
    }

    private void setParamenters(Locale locale) {
        mDiscoverDTO = new DiscoverDTO();

        mDiscoverDTO.setSortBy("popularity.desc");
        mDiscoverDTO.setReleaseDateGte(formatter.format(mAtualMinDate.getTime()));
        mDiscoverDTO.setReleaseDateLte(formatter.format(mAtualMaxDate.getTime()));
        mDiscoverDTO.setWithReleaseType("2|3");
        mDiscoverDTO.setRegion(LocaleUtils.getLocaleCountryISO(locale));

    }

    public void onClickNext(Locale locale) {
        setDateByDays(mAtualMaxDate, DAYS_OF_WEEK);
        setDateByDays(mAtualMinDate, DAYS_OF_WEEK);

        updateValues(locale);
    }

    public void onClickAnterior(Locale locale) {
        setDateByDays(mAtualMaxDate, -DAYS_OF_WEEK);
        setDateByDays(mAtualMinDate, -DAYS_OF_WEEK);

        updateValues(locale);
    }

    private void setDateByDayOfWeek(Calendar date, int dayOfWeek) {
        date.set(Calendar.DAY_OF_WEEK, dayOfWeek);
    }

    private void setDateByDays(Calendar date, int numOfDays) {
        date.add(Calendar.DATE, numOfDays);
    }

}
