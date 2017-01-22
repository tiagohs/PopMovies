package br.com.tiagohs.popmovies.presenter;

import android.app.Activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import br.com.tiagohs.popmovies.util.LocaleUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.enumerations.Param;
import br.com.tiagohs.popmovies.util.enumerations.ParamSortBy;
import br.com.tiagohs.popmovies.view.LancamentosSemanaView;

public class LancamentosSemanaPresenterImpl implements LancamentosSemanaPresenter {

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
        setAtualMinMinDate();
        setAtualMaxMinDate();

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
        setNextMaxAtualDate();
        setNextMinAtualDate();

        updateValues(locale);
    }

    public void onClickAnterior(Locale locale) {
        setAnteriorMaxAtualDate();
        setAnteriorMinAtualDate();

        updateValues(locale);
    }

    private void setAtualMinMinDate() {
        mAtualMinDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
    }

    private void setAtualMaxMinDate() {
        mAtualMaxDate.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
    }

    private void setNextMinAtualDate() {
        mAtualMinDate.add(Calendar.DATE, 7);
    }

    private void setNextMaxAtualDate() {
        mAtualMaxDate.add(Calendar.DATE, 7);
    }

    private void setAnteriorMinAtualDate() {
        mAtualMinDate.add(Calendar.DATE, -7);
    }

    private void setAnteriorMaxAtualDate() {
        mAtualMaxDate.add(Calendar.DATE, -7);
    }

    @Override
    public void setView(LancamentosSemanaView view) {
        mView = view;
    }

    @Override
    public void onCancellRequest(Activity activity, String tag) {

    }
}
