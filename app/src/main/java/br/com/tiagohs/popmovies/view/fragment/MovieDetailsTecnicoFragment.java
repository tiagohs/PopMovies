package br.com.tiagohs.popmovies.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.Movie.MovieDetails;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.view.adapters.ElencoAdapter;
import br.com.tiagohs.popmovies.view.adapters.EquipeTecnicaAdapter;
import br.com.tiagohs.popmovies.view.adapters.KeywordsAdapter;
import butterknife.BindView;

/**
 * Created by Tiago Henrique on 27/08/2016.
 */
public class MovieDetailsTecnicoFragment extends BaseFragment {
    private static final String TAG = MovieDetailsTecnicoFragment.class.getSimpleName();

    private static final String ARG_MOVIE = "movie";

    @BindView(R.id.movie_details_titulo_original)
    TextView mTituloOriginal;

    @BindView(R.id.movie_details_idioma_original)
    TextView mIdiomaOriginal;

    @BindView(R.id.movie_details_orcamento_original)
    TextView mOcamento;

    @BindView(R.id.movie_details_receita_original)
    TextView mReceita;

    @BindView(R.id.elenco_recycler_view)
    RecyclerView mElencoRecyclerView;

    @BindView(R.id.equipe_tecnica_recycler_view)
    RecyclerView mEquipeTecnicaRecyclerView;

    @BindView(R.id.keywords_recycler_view)
    RecyclerView mKeywordsRecyclerView;

    private MovieDetails mMovie;

    public static MovieDetailsTecnicoFragment newInstance(MovieDetails movie) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MOVIE, movie);

        MovieDetailsTecnicoFragment movieDetailsTecnicoFragment = new MovieDetailsTecnicoFragment();
        movieDetailsTecnicoFragment.setArguments(bundle);

        return movieDetailsTecnicoFragment;
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_movie_detail_tecnico;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMovie = (MovieDetails) getArguments().getSerializable(ARG_MOVIE);
        Log.i(TAG, "ID do Filme: " + mMovie.getId());

        mTituloOriginal.setText(mMovie.getOriginalTitle());
        mIdiomaOriginal.setText(MovieUtils.formatIdioma(getActivity(), mMovie.getOriginalLanguage()));
        mOcamento.setText(MovieUtils.formatCurrency(mMovie.getBudget()));
        mReceita.setText(MovieUtils.formatCurrency(mMovie.getRevenue()));

        mElencoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        mElencoRecyclerView.setAdapter(new ElencoAdapter(getActivity(), mMovie.getCast()));

        mEquipeTecnicaRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        mEquipeTecnicaRecyclerView.setAdapter(new EquipeTecnicaAdapter(getActivity(), mMovie.getCrew()));

        mKeywordsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        mKeywordsRecyclerView.setAdapter(new KeywordsAdapter(getActivity(), mMovie.getKeywords()));
    }
}
