package br.com.tiagohs.popmovies.presenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import br.com.tiagohs.popmovies.util.LocaleUtils;
import br.com.tiagohs.popmovies.util.enumerations.Param;
import br.com.tiagohs.popmovies.util.enumerations.ParamSortBy;
import br.com.tiagohs.popmovies.view.LancamentosSemanaView;

public class LancamentosSemanaPresenterImpl implements LancamentosSemanaPresenter {
    private static final int DAYS_OF_WEEK = 7;

    private LancamentosSemanaView mView;

    private Calendar mAtualMinDate;
    private Calendar mAtualMaxDate;

    private SimpleDateFormat formatter;
    private SimpleDateFormat formatterDateView;

    private Map<String, String> mLancamentosParameters;

    public LancamentosSemanaPresenterImpl() {
        mAtualMinDate = Calendar.getInstance();
        mAtualMaxDate = Calendar.getInstance();

        formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatterDateView = new SimpleDateFormat("dd MMM yyyy");
    }

    public void initUpdateMovies(Locale locale) {
        setDateByDayOfWeek(mAtualMinDate, Calendar.SUNDAY);
        setDateByDayOfWeek(mAtualMaxDate, Calendar.SATURDAY);

        updateValues(locale);
    }

    private void updateValues(Locale locale) {
        setParamenters(locale);

        mView.updateListMovies(mLancamentosParameters);
        mView.updateDateText(formatterDateView.format(mAtualMinDate.getTime()), formatterDateView.format(mAtualMaxDate.getTime()));
    }

    private void setParamenters(Locale locale) {
        mLancamentosParameters = new HashMap<>();

        mLancamentosParameters.put(Param.RELEASE_DATE_GTE.getParam(), formatter.format(mAtualMinDate.getTime()));
        mLancamentosParameters.put(Param.RELEASE_DATE_LTE.getParam(), formatter.format(mAtualMaxDate.getTime()));
        mLancamentosParameters.put(Param.RELEASE.getParam(), LocaleUtils.getLocaleCountryISO(locale));
        mLancamentosParameters.put(Param.WITH_RELEASE_TYPE.getParam(), "2|3");
        mLancamentosParameters.put(Param.SORT_BY.getParam(), ParamSortBy.POPULARITY_DESC.getValue());
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

    @Override
    public void setView(LancamentosSemanaView view) {
        mView = view;
    }

}
