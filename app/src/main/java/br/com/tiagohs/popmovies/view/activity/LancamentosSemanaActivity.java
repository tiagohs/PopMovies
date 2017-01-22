package br.com.tiagohs.popmovies.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.LocaleDTO;
import br.com.tiagohs.popmovies.presenter.LancamentosSemanaPresenter;
import br.com.tiagohs.popmovies.util.LocaleUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.LancamentosSemanaView;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.view.fragment.ListMoviesDefaultFragment;
import butterknife.BindView;
import butterknife.OnClick;

public class LancamentosSemanaActivity extends BaseActivity implements LancamentosSemanaView, ListMoviesCallbacks {

    @BindView(R.id.btn_proximo)
    Button mProximo;

    @BindView(R.id.btn_anterior)
    Button mAnterior;

    @BindView(R.id.text_datas)
    TextView mDatas;

    @BindView(R.id.spinner_country)
    Spinner mCountrys;

    @Inject
    LancamentosSemanaPresenter mPresenter;

    private ArrayAdapter<LocaleDTO> mCountryAdapter;
    private List<LocaleDTO> mCountrysDTO;

    private Locale mLocale;

    public static Intent newIntent(Context context) {
        return new Intent(context, LancamentosSemanaActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        mPresenter.setView(this);

        setActivityTitle("Lançamentos da Semana");

        mLocale = LocaleUtils.getLocaleAtual();
        mPresenter.initUpdateMovies(mLocale);

        configureCountryAdapter();
    }

    private void configureCountryAdapter() {
        mCountrysDTO = LocaleUtils.getAllCountrysDTO();
        mCountryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mCountrysDTO);
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

    public void updateListMovies(Map<String, String> lancamentosParameters) {
        startFragment(R.id.container_movies, ListMoviesDefaultFragment.newInstance(Sort.DISCOVER, R.layout.item_list_movies, R.layout.fragment_list_movies_default_no_pull, lancamentosParameters, ListMoviesDefaultFragment.createGridListArguments(getResources().getInteger(R.integer.movies_columns))));
    }

    public void updateDateText(String dateMin, String dateMax) {
        mDatas.setText(getString(R.string.lanc_date_min_max, dateMin, dateMax));
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_lancamentos_semana;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }

    @Override
    protected int getMenuLayoutID() {
        return R.menu.menu_principal;
    }

    @Override
    public void setProgressVisibility(int visibityState) {

    }

    @Override
    public boolean isAdded() {
        return this != null;
    }

    @Override
    public void onMovieSelected(int movieID, ImageView imageView) {
        ViewUtils.startMovieActivityWithTranslation(this, movieID, imageView, getString(R.string.poster_movie));
    }
}
