package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCast;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCrew;
import br.com.tiagohs.popmovies.model.dto.ItemListDTO;
import br.com.tiagohs.popmovies.model.dto.PersonListDTO;
import br.com.tiagohs.popmovies.model.keyword.Keyword;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.enumerations.ItemType;
import br.com.tiagohs.popmovies.view.adapters.ListWordsAdapter;
import br.com.tiagohs.popmovies.view.adapters.PersonAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ListWordsCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.PersonCallbacks;
import butterknife.BindView;

/**
 * Created by Tiago Henrique on 27/08/2016.
 */
public class MovieDetailsTecnicoFragment extends BaseFragment {
    private static final String TAG = MovieDetailsTecnicoFragment.class.getSimpleName();

    private static final String ARG_MOVIE = "movie";

    @BindView(R.id.movie_details_titulo_original)           TextView mTituloOriginal;
    @BindView(R.id.movie_details_idioma_original)           TextView mIdiomaOriginal;
    @BindView(R.id.movie_details_orcamento_original)        TextView mOcamento;
    @BindView(R.id.movie_details_receita_original)          TextView mReceita;
    @BindView(R.id.elenco_recycler_view)                    RecyclerView mElencoRecyclerView;
    @BindView(R.id.equipe_tecnica_recycler_view)            RecyclerView mEquipeTecnicaRecyclerView;
    @BindView(R.id.keywords_recycler_view)                  RecyclerView mKeywordsRecyclerView;
    @BindView(R.id.label_movie_details_titulo_original)     TextView mLabelTituloOriginal;
    @BindView(R.id.label_movie_details_idioma_original)     TextView mLabelIdiomaOriginal;
    @BindView(R.id.label_movie_details_orcamento_original)  TextView mLabelOcamento;
    @BindView(R.id.label_movie_details_receita_original)    TextView mLabelReceita;

    private MovieDetails mMovie;

    private PersonCallbacks mCallbacks;
    private ListWordsCallbacks mKeyWordsCallbacks;

    public static MovieDetailsTecnicoFragment newInstance(MovieDetails movie) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MOVIE, movie);

        MovieDetailsTecnicoFragment movieDetailsTecnicoFragment = new MovieDetailsTecnicoFragment();
        movieDetailsTecnicoFragment.setArguments(bundle);

        return movieDetailsTecnicoFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (PersonCallbacks) context;
        mKeyWordsCallbacks = (ListWordsCallbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
        mKeyWordsCallbacks = null;
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_movie_detail_tecnico;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMovie = (MovieDetails) getArguments().getSerializable(ARG_MOVIE);
        updateView();
    }

    private void updateView() {

        mTituloOriginal.setText(mMovie.getOriginalTitle());
        mLabelTituloOriginal.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));

        mIdiomaOriginal.setText(MovieUtils.formatIdioma(getActivity(), mMovie.getOriginalLanguage()));
        mLabelIdiomaOriginal.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));

        mOcamento.setText(MovieUtils.formatCurrency(mMovie.getBudget()));
        mLabelOcamento.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));

        mReceita.setText(MovieUtils.formatCurrency(mMovie.getRevenue()));
        mLabelReceita.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));

        mElencoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        mElencoRecyclerView.setAdapter(new PersonAdapter(getActivity(), getCastDTO(mMovie.getCast()), mCallbacks));

        mEquipeTecnicaRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        mEquipeTecnicaRecyclerView.setAdapter(new PersonAdapter(getActivity(), getCrewDTO(mMovie.getCrew()), mCallbacks));

        mKeywordsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        mKeywordsRecyclerView.setAdapter(new ListWordsAdapter(getActivity(), getKeywordsItems(mMovie.getKeywords()), mKeyWordsCallbacks, ItemType.KEYWORD));
    }

    private List<ItemListDTO> getKeywordsItems(List<Keyword> keywords) {
        List<ItemListDTO> list = new ArrayList<>();

        for (Keyword k : keywords)
            list.add(new ItemListDTO(k.getId(), k.getName()));

        return list;
    }

    private List<PersonListDTO> getCastDTO(List<MediaCreditCast> cast) {
        List<PersonListDTO> personListDTO = new ArrayList<>();

        for (MediaCreditCast c : cast)
            personListDTO.add(new PersonListDTO(c.getId(), c.getArtworkPath(), c.getName(), c.getCharacter()));

        return personListDTO;
     }

    private List<PersonListDTO> getCrewDTO(List<MediaCreditCrew> cast) {
        List<PersonListDTO> personListDTO = new ArrayList<>();

        for (MediaCreditCrew c : cast)
            personListDTO.add(new PersonListDTO(c.getId(), c.getArtworkPath(), c.getName(), c.getDepartment()));

        return personListDTO;
    }
}
