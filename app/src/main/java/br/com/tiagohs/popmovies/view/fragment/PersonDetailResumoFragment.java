package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.model.dto.ListActivityDTO;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.util.DTOUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ListType;
import br.com.tiagohs.popmovies.util.enumerations.Sort;
import br.com.tiagohs.popmovies.view.activity.ListsDefaultActivity;
import br.com.tiagohs.popmovies.view.activity.WallpapersActivity;
import br.com.tiagohs.popmovies.view.activity.WebViewActivity;
import br.com.tiagohs.popmovies.view.adapters.ImageAdapter;
import br.com.tiagohs.popmovies.view.adapters.ListMoviesAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ImagesCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import butterknife.BindView;
import butterknife.OnClick;

public class PersonDetailResumoFragment extends BaseFragment  {
    private static final String ARG_PERSON = "br.com.tiagohs.popmovies.person";

    private static final int NUM_MAX_KNOW_FOR_MOVIES = 10;
    private static final int NUM_MAX_IMAGES = 12;

    @BindView(R.id.descricao_person)                TextView mDescricaoPerson;
    @BindView(R.id.person_data_nascimento)          TextView mDataNascimento;
    @BindView(R.id.person_cidade_natal)             TextView mCidadeNatal;
    @BindView(R.id.person_genero)                   TextView mGenero;
    @BindView(R.id.person_principais_areas)         TextView mPrincipaisAreas;
    @BindView(R.id.conhecido_por_recycler_view)     RecyclerView mKnowForRecyclerView;
    @BindView(R.id.imagens_recycler_view)           RecyclerView mImagensRecyclerView;
    @BindView(R.id.wallpapers_container)            LinearLayout mWallpapersContainer;
    @BindView(R.id.know_for_container)              LinearLayout mKnowForContainer;

    private PersonInfo mPerson;
    private ListMoviesCallbacks mCallbacks;
    private ImagesCallbacks mImagesCallbacks;
    private ListMoviesAdapter mKnowForAdapter;
    private ImageAdapter mImageAdapter;

    private List<String> mAreasAtuacao;
    private List<ImageDTO> mTotalImagesDTO;
    private List<MovieListDTO> mListKnowForDTO;


    public PersonDetailResumoFragment() {
        mAreasAtuacao = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (ListMoviesCallbacks) context;
        mImagesCallbacks = (ImagesCallbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
        mImagesCallbacks = null;
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_person_detail_resumo;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    public static PersonDetailResumoFragment newInstance(PersonInfo personInfo) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_PERSON, personInfo);

        PersonDetailResumoFragment personDetailResumoFragment = new PersonDetailResumoFragment();
        personDetailResumoFragment.setArguments(bundle);

        return personDetailResumoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPerson = (PersonInfo) getArguments().getSerializable(ARG_PERSON);
    }

    @Override
    public void onStart() {
        super.onStart();

        updatePersonInfo();
    }

    public void updatePersonInfo() {
        mCidadeNatal.setText(mPerson.getPlaceOfBirth() != null ? mPerson.getPlaceOfBirth() : "--");

        updateGenero();
        updateDataNascimento();
        updateKnowFor();
        updateAreasAtuacoes();
        updateDescricao();
        setupImagesRecyclerView();
    }

    private void updateGenero() {
        String genero = null;

        switch (mPerson.getGender()) {
            case MALE:
                genero = getString(R.string.genero_masculino);
                break;
            case FEMALE:
                genero = getString(R.string.genero_feminino);
                break;
            case UNKNOWN:
                genero = "--";
                break;
            default:
                genero = "--";
        }
        mGenero.setText(genero);
    }

    private void updateDataNascimento() {
        int age = MovieUtils.getAge(mPerson.getYear(), mPerson.getMonth(), mPerson.getDay());
        mDataNascimento.setText(mPerson.getBirthday() != null && mPerson.getBirthday() != "" ?
                getString(R.string.data_nascimento_formatado, MovieUtils.formateDate(getActivity(), mPerson.getBirthday()), age) + " " + getResources().getQuantityString(R.plurals.number_idade, age) :
                "--");
    }

    private void updateKnowFor() {

        if (!mPerson.getMovieCredits().getCast().isEmpty() || !mPerson.getMovieCredits().getCrew().isEmpty()) {
            mListKnowForDTO = DTOUtils.createPersonKnowForMoviesDTO(mPerson.getMovieCredits().getCast(), NUM_MAX_KNOW_FOR_MOVIES);
            mListKnowForDTO.addAll(DTOUtils.createPersonKnowForMoviesDTO(mPerson.getMovieCredits().getCrew(), NUM_MAX_KNOW_FOR_MOVIES));

            mKnowForAdapter = new ListMoviesAdapter(getActivity(), mListKnowForDTO, mCallbacks, R.layout.item_similares_movie);
            mKnowForRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
            mKnowForRecyclerView.setAdapter(mKnowForAdapter);
        } else
            mKnowForContainer.setVisibility(View.GONE);
    }

    private void updateAreasAtuacoes() {
        mPrincipaisAreas.setText(MovieUtils.formatList(mPerson.getAreasAtuacao()));
    }

    private void updateDescricao() {
        mDescricaoPerson.setText(MovieUtils.isEmptyValue(mPerson.getBiography())
                                            ? ViewUtils.createDefaultPersonBiography(mPerson.getName(), MovieUtils.formatList(mAreasAtuacao),
                                                                                     mListKnowForDTO.isEmpty() ? new ArrayList<MovieListDTO>() : mListKnowForDTO)
                                                                                     : mPerson.getBiography());
    }

    private void setupImagesRecyclerView() {

        if (!mPerson.getTaggedImages().isEmpty() || !mPerson.getImages().isEmpty()) {
            List<Artwork> totalImages = getTotalPersonImages();
            List<ImageDTO> imageDTOs = DTOUtils.createPersonImagesDTO(mPerson, NUM_MAX_IMAGES, totalImages);

            mTotalImagesDTO = DTOUtils.createPersonImagesDTO(mPerson, totalImages.size(), totalImages);
            int columnCount = getResources().getInteger(R.integer.images_movie_detail_columns);
            mImagensRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));
            mImageAdapter = new ImageAdapter(getActivity(), imageDTOs, mImagesCallbacks, mTotalImagesDTO);
            mImagensRecyclerView.setAdapter(mImageAdapter);
        } else
            mWallpapersContainer.setVisibility(View.GONE);

    }

    private List<Artwork> getTotalPersonImages() {
        List<Artwork> images = new ArrayList<>();
        images.addAll(mPerson.getTaggedImages());
        images.addAll(mPerson.getImages());

        return images;
    }

    @OnClick(R.id.wallpapers_riple)
    public void onClickPersonWallpapers() {
        startActivity(WallpapersActivity.newIntent(getActivity(), mTotalImagesDTO, getString(R.string.wallpapers_title, mPerson.getName())));
    }

    @OnClick(R.id.conhecido_por_riple)
    public void onClickConhecidoPorWallpapers() {
        startActivity(ListsDefaultActivity.newIntent(getActivity(), new ListActivityDTO(mPerson.getId(), getString(R.string.conhecido_por_title_activity), mPerson.getName(), Sort.PERSON_CONHECIDO_POR, R.layout.item_list_movies, ListType.MOVIES)));
    }

    @OnClick(R.id.riple_wiki)
    public void onClickWiki() {
        startActivityForResult(WebViewActivity.newIntent(getActivity(), getString(R.string.person_wiki, mPerson.getName()), mPerson.getName()), 0);
    }

    @OnClick(R.id.riple_imdb)
    public void onClickIMDB() {
        startActivityForResult(WebViewActivity.newIntent(getActivity(), getString(R.string.person_imdb, mPerson.getImdbId()), mPerson.getName()), 0);
    }
}
