package br.com.tiagohs.popmovies.view.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.data.repository.ProfileRepositoryImpl;
import br.com.tiagohs.popmovies.model.dto.CarrerMoviesDTO;
import br.com.tiagohs.popmovies.model.dto.GenrerMoviesDTO;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.presenter.PerfilEstatisticasPresenter;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
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

    @BindView(R.id.genrers_graphic)
    PieChart mGenrerGraphic;

    @BindView(R.id.total_movies_watched)
    TextView mTotalMoviesWatched;

    @BindView(R.id.total_movies_favorites)
    TextView mTotalMoviesFavorite;

    @BindView(R.id.total_movies_want_see)
    TextView mTotalMoviesWanSee;

    @BindView(R.id.total_movies_dont_want_see)
    TextView mTotalMoviesDontWanSee;

    @BindView(R.id.resumo_dados_pessoais)
    TextView mResumoDadosPessoais;

    @BindView(R.id.descricao_dados_pessoais)
    TextView mDescricaoDadosPessoais;

    @BindView(R.id.graph_nao_ha_dados)              TextView mNaoHaDados;
    @BindView(R.id.horas_nao_ha_dados)              TextView mHorasNaoHaDados;
    @BindView(R.id.horas_container)                 LinearLayout mHorasContainer;
    @BindView(R.id.principal_progress)
    ProgressWheel mProgressWheel;

    @BindView(R.id.principal_container)
    LinearLayout mContainerPrincipal;


    private Legend mLegend;
    private boolean mIsEstatisticasLoaded = false;

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
        mPresenter.setProfileRepository(new ProfileRepositoryImpl(getContext()));
        mPresenter.setUsername(PrefsUtils.getCurrentUser(getContext()).getUsername());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !mIsEstatisticasLoaded ) {
            mPresenter.initUpdates();
            mIsEstatisticasLoaded = true;
        }
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
            mHorasContainer.setVisibility(View.GONE);
            mHorasNaoHaDados.setVisibility(View.VISIBLE);
        }

    }

    public void setTotalFilmesAssistidos(int totalFilmesAssistidos) {
        mTotalFilmesAssistidos.setText(String.valueOf(totalFilmesAssistidos));
        mTotalFilmesAssistidosLabel.setText(getResources().getQuantityString(R.plurals.number_of_filmes_assistidos, totalFilmesAssistidos));
    }

    public void setDadosPessoais(String pais, Calendar birthday, String genero) {
        int idade;

        if (birthday != null && !ViewUtils.isEmptyValue(genero)) {
            idade = MovieUtils.getAge(birthday);
            mResumoDadosPessoais.setText(getString(R.string.perfil_dados_pais_idade_genero,
                    pais, String.valueOf(idade), getResources().getQuantityString(R.plurals.number_idade, idade), genero));
        } else if (birthday != null && ViewUtils.isEmptyValue(genero)) {
            idade = MovieUtils.getAge(birthday);
            mResumoDadosPessoais.setText(getString(R.string.perfil_dados_pais_idade,
                    pais, String.valueOf(idade), getResources().getQuantityString(R.plurals.number_idade, idade)));
        } else if (birthday == null && !ViewUtils.isEmptyValue(genero)) {
            mResumoDadosPessoais.setText(getString(R.string.perfil_dados_pais_genero, pais, genero));
        } else {
            mResumoDadosPessoais.setText(getString(R.string.perfil_dados_pais, pais));
        }

        mResumoDadosPessoais.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
    }

    public void setTotalsMovies(long totalWatched, long totalFavorite, long totalWantSee, long totalDontWantSee) {
        setTotalsMovies(totalWatched, mTotalMoviesWatched);
        setTotalsMovies(totalFavorite, mTotalMoviesFavorite);
        setTotalsMovies(totalWantSee, mTotalMoviesWanSee);
        setTotalsMovies(totalDontWantSee, mTotalMoviesDontWanSee);
    }

    public void setDescricao(String descricao) {
        if (descricao != null)
            mDescricaoDadosPessoais.setText(descricao);
        else
            mDescricaoDadosPessoais.setText(R.string.nao_ha_descicao);

        mDescricaoDadosPessoais.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
    }

    private void setTotalsMovies(long total, TextView mTexView) {
        mTexView.setText(String.valueOf(total) + " " + getResources().getQuantityString(R.plurals.number_of_filmes_assistidos, (int) total));
        mTexView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
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

    public void configurateGraphic(List<GenrerMoviesDTO> genres) {

        if (genres.isEmpty()) {
            mNaoHaDados.setVisibility(View.VISIBLE);
            mGenrerGraphic.setVisibility(View.GONE);
        } else {
            mGenrerGraphic.setUsePercentValues(true);
            Description d = new Description();
            d.setText("Seus Gêneros Favoritos!");
            mGenrerGraphic.setDescription(d);

            mGenrerGraphic.setDrawHoleEnabled(true);
            mGenrerGraphic.setHoleRadius(7);
            mGenrerGraphic.setTransparentCircleRadius(10);

            mGenrerGraphic.setRotationAngle(0);
            mGenrerGraphic.setRotationEnabled(true);

            addData(genres);

            mLegend = mGenrerGraphic.getLegend();

            mLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
            mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
            mLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            mLegend.setDrawInside(false);
            mLegend.setXEntrySpace(20f);
            mLegend.setYEntrySpace(0f);
            mLegend.setYOffset(0f);

            mGenrerGraphic.setEntryLabelTextSize(12f);
        }

    }

    private void addData(List<GenrerMoviesDTO> genres) {
        List<PieEntry> entries = new ArrayList<>();

        Collections.sort(genres, new Comparator<GenrerMoviesDTO>() {
            public int compare(GenrerMoviesDTO genre1, GenrerMoviesDTO genre2) {
                return genre2.getTotalMovies().compareTo(genre1.getTotalMovies());
            }
        });

        if (genres.size() > 5) {
            for (int cont = 0; cont < 6; cont++) {
                entries.add(new PieEntry(genres.get(cont).getTotalMovies(), getString(genres.get(cont).getGenrerName())));
            }

            int totalMovies = 0;
            for (int cont = 6; cont < genres.size(); cont++) {
                totalMovies += genres.get(cont).getTotalMovies();
            }

            entries.add(new PieEntry(totalMovies,  getString(R.string.genres_outros)));
        } else {
            for (GenrerMoviesDTO g : genres) {
                entries.add(new PieEntry(g.getTotalMovies(), getString(g.getGenrerName())));
            }
        }

        PieDataSet dataset = new PieDataSet(entries, "");
        dataset.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData pieData = new PieData(dataset);
        mGenrerGraphic.setData(pieData);
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
        mProgressWheel.setVisibility(visibityState);
    }

    public void setContainerPrincipalVisibility(int visibility) {
        mContainerPrincipal.setVisibility(visibility);
    }

}