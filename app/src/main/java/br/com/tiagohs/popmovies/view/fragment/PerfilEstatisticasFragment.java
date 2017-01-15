package br.com.tiagohs.popmovies.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.HashMap;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.presenter.PerfilEstatisticasPresenter;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.BaseView;
import br.com.tiagohs.popmovies.view.PerfilEstatisticasView;
import br.com.tiagohs.popmovies.view.activity.ListsDefaultActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class PerfilEstatisticasFragment extends BaseFragment implements PerfilEstatisticasView {

    @BindView(R.id.total_anos)
    TextView mTotalAnosAssistidos;

    @BindView(R.id.label_total_anos)
    TextView mTotalAnosAssistidosLabel;

    @BindView(R.id.container_total_anos)
    LinearLayout mTotalAnosAssistidosContainer;

    @BindView(R.id.total_meses)
    TextView mTotalMesesAssistidos;

    @BindView(R.id.label_total_meses)
    TextView mTotalMesesAssistidosLabel;

    @BindView(R.id.container_total_meses)
    LinearLayout mTotalMesesAssistidosContainer;

    @BindView(R.id.total_dias)
    TextView mTotalDiasAssistidos;

    @BindView(R.id.label_total_dias)
    TextView mTotalDiasAssistidosLabel;

    @BindView(R.id.container_total_dias)
    LinearLayout mTotalDiasAssistidosContainer;

    @BindView(R.id.total_horas)
    TextView mTotalHorasAssistidos;

    @BindView(R.id.label_total_horas)
    TextView mTotalHorasAssistidosLabel;

    @BindView(R.id.container_total_horas)
    LinearLayout mTotalHorasAssistidosContainer;

    @BindView(R.id.total_minutos)
    TextView mTotalMinutosAssistidos;

    @BindView(R.id.label_total_minutos)
    TextView mTotalMinutosAssistidosLabel;

    @BindView(R.id.container_total_minutos)
    LinearLayout mTotalMinutosAssistidosContainer;

    @BindView(R.id.total_horas_riple)
    MaterialRippleLayout mTotalHorasRiple;

    @BindView(R.id.total_filmes)
    TextView mTotalFilmesAssistidos;

    @BindView(R.id.label_total_filmes)
    TextView mTotalFilmesAssistidosLabel;

    @BindView(R.id.dados_iniciais_container)
    LinearLayout mDadosIniciaisContainer;

    @Inject
    PerfilEstatisticasPresenter mPresenter;

    public static PerfilEstatisticasFragment newInstance() {
        PerfilEstatisticasFragment perfilEstatisticasFragment = new PerfilEstatisticasFragment();
        return perfilEstatisticasFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getApplicationComponent().inject(this);

        mPresenter.setView(this);
        mPresenter.setContext(getContext());

        mPresenter.initUpdates();
    }

    public void setTotalHorasAssistidas(long duracaoTotal) {

        if (duracaoTotal > 0) {
            int years = (int) duracaoTotal/(365*24*60);
            int months = (int) (duracaoTotal%(365*24*60))/(12*24*60);
            int days = (int) (duracaoTotal%(365*24*60))/(24*60);
            int hours= (int) (duracaoTotal%(365*24*60)) / 60;
            int minutes = (int) (duracaoTotal%(365*24*60)) % 60;

            setTotalHorasVisibility(mTotalAnosAssistidosContainer, mTotalAnosAssistidos, mTotalAnosAssistidosLabel, R.plurals.number_of_year, years);
            setTotalHorasVisibility(mTotalMesesAssistidosContainer, mTotalMesesAssistidos, mTotalMesesAssistidosLabel, R.plurals.number_of_month, months);
            setTotalHorasVisibility(mTotalDiasAssistidosContainer, mTotalDiasAssistidos, mTotalDiasAssistidosLabel, R.plurals.number_of_days, days);
            setTotalHorasVisibility(mTotalHorasAssistidosContainer, mTotalHorasAssistidos, mTotalHorasAssistidosLabel, R.plurals.number_of_hours, hours);
            setTotalHorasVisibility(mTotalMinutosAssistidosContainer, mTotalMinutosAssistidos, mTotalMinutosAssistidosLabel, R.plurals.number_of_minute, minutes);
        } else {
            mTotalHorasRiple.setVisibility(View.GONE);
        }

    }

    public void setTotalFilmesAssistidos(int totalFilmesAssistidos) {
        mTotalFilmesAssistidos.setText(String.valueOf(totalFilmesAssistidos));
        mTotalFilmesAssistidosLabel.setText(getResources().getQuantityString(R.plurals.number_of_filmes_assistidos, totalFilmesAssistidos));
    }

    private void setTotalHorasVisibility(LinearLayout container, TextView numerTextView, TextView label, int idLabelString, int value) {
        if (value == 0)
            container.setVisibility(View.GONE);
        else {
            numerTextView.setText(String.valueOf(value));
            label.setText(getResources().getQuantityString(idLabelString, value));
        }
    }

    @OnClick({R.id.filmes_total_person_riple, R.id.total_horas_riple})
    public void onClickTotalFilmesAssistidos() {
        startActivity(ListsDefaultActivity.newIntent(getContext(), new ListActivityDTO(0, getString(R.string.assistidos), Sort.ASSISTIDOS, R.layout.item_list_movies, ListType.MOVIES), new HashMap<String, String>()));
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_perfil_estatisticas;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    public void setProgressVisibility(int visibityState) {

    }
}
