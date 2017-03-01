package br.com.tiagohs.popmovies.ui.view.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.ui.contracts.LancamentosSemanaContract;
import br.com.tiagohs.popmovies.model.dto.DiscoverDTO;
import br.com.tiagohs.popmovies.model.dto.LocaleDTO;
import br.com.tiagohs.popmovies.util.LocaleUtils;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import butterknife.BindView;
import butterknife.OnClick;

public class LancamentosSemanaFragment extends BaseFragment implements LancamentosSemanaContract.LancamentosSemanaView {

    @BindView(R.id.btn_proximo)             Button mProximo;
    @BindView(R.id.btn_anterior)            Button mAnterior;
    @BindView(R.id.text_datas)              TextView mDatas;
    @BindView(R.id.spinner_country)         Spinner mCountrys;
    @BindView(R.id.lancamentos_calendario)  LinearLayout mLancamentosCalendario;

    @Inject
    LancamentosSemanaContract.LancamentosSemanaPresenter mPresenter;

    private DatePickerDialog.OnDateSetListener mLancamentosDatePicker;
    private Calendar mCalendarLancamento;

    public static LancamentosSemanaFragment newInstance() {
        LancamentosSemanaFragment lancamentosSemanaFragment = new LancamentosSemanaFragment();
        return lancamentosSemanaFragment;
    }

    private ArrayAdapter<LocaleDTO> mCountryAdapter;
    private List<LocaleDTO> mCountrysDTO;

    private Locale mLocale;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getApplicationComponent().inject(this);

        mPresenter.onBindView(this);

        mLocale = LocaleUtils.getLocaleAtual();
        mPresenter.initUpdateMovies(mLocale);

        configureCountryAdapter();
        configureCalendar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onUnbindView();
    }

    private void configureCalendar() {
        mCalendarLancamento = Calendar.getInstance();
        mLancamentosDatePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mCalendarLancamento.set(Calendar.YEAR, year);
                mCalendarLancamento.set(Calendar.MONTH, monthOfYear);
                mCalendarLancamento.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mPresenter.updateWithCalendar(mCalendarLancamento, mLocale);
            }
        };

        mLancamentosCalendario.setOnClickListener(onClickLancamentosCalendario());
    }

    private View.OnClickListener onClickLancamentosCalendario() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), mLancamentosDatePicker, mCalendarLancamento
                        .get(Calendar.YEAR), mCalendarLancamento.get(Calendar.MONTH),
                        mCalendarLancamento.get(Calendar.DAY_OF_MONTH)).show();
            }
        };
    }

    private void configureCountryAdapter() {
        mCountrysDTO = LocaleUtils.getAllCountrysDTO();
        mCountryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mCountrysDTO);
        mCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCountrys.setAdapter(mCountryAdapter);

        int spinnerPosition = mCountryAdapter.getPosition(new LocaleDTO(LocaleUtils.getLocaleCountryName()));
        mCountrys.setSelection(spinnerPosition);
        mCountrys.setOnItemSelectedListener(onCountrySelected());
    }

    private AdapterView.OnItemSelectedListener onCountrySelected() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mLocale = mCountrysDTO.get(position).getLocale();
                mPresenter.initUpdateMovies(mLocale);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    @OnClick(R.id.btn_anterior)
    public void onClickAnterior() {
        mPresenter.onClickAnterior(mLocale);
    }

    @OnClick(R.id.btn_proximo)
    public void onClickPróximo() {
        mPresenter.onClickNext(mLocale);
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_lancamentos_semana;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    public void updateListMovies(DiscoverDTO discoverDTO) {
        startFragment(R.id.container_movies, ListMoviesDefaultFragment.newInstance(Sort.DISCOVER, R.layout.item_list_movies, R.layout.fragment_list_movies_default_no_pull, discoverDTO, ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns))));
    }

    @Override
    public void updateDateText(String dateMin, String dateMax) {
        mDatas.setText(getString(R.string.lanc_date_min_max, dateMin, dateMax));
    }

    @Override
    public void setProgressVisibility(int visibityState) {

    }
}
