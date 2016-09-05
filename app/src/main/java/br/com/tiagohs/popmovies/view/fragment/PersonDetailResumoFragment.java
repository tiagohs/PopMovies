package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.graphics.Typeface;
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
import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.enumerations.Gender;
import br.com.tiagohs.popmovies.view.adapters.ImageAdapter;
import br.com.tiagohs.popmovies.view.adapters.ListMoviesAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ImagesCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import butterknife.BindView;

public class PersonDetailResumoFragment extends BaseFragment  {
    private static final String ARG_PERSON = "br.com.tiagohs.popmovies.person";

    @BindView(R.id.descricao_person)
    TextView mDescricaoPerson;

    @BindView(R.id.person_data_nascimento)
    TextView mDataNascimento;

    @BindView(R.id.person_cidade_natal)
    TextView mCidadeNatal;

    @BindView(R.id.person_genero)
    TextView mGenero;

    @BindView(R.id.person_principais_areas)
    TextView mPrincipaisAreas;

    @BindView(R.id.conhecido_por_recycler_view)
    RecyclerView mKnowForRecyclerView;

    @BindView(R.id.imagens_recycler_view)
    RecyclerView mImagensRecyclerView;

    @BindView(R.id.person_detail_images_title_container)
    LinearLayout mImagesTitleContainer;

    private PersonInfo mPerson;
    private ListMoviesCallbacks mCallbacks;
    private ImagesCallbacks mImagesCallbacks;
    private ListMoviesAdapter mKnowForAdapter;
    private ImageAdapter mImageAdapter;

    private List<String> mAreasAtuacao;

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

        atualizarView();
    }

    public void atualizarView() {

        mCidadeNatal.setText(mPerson.getPlaceOfBirth() != null ? mPerson.getPlaceOfBirth() : "--");

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
        int age = MovieUtils.getAge(mPerson.getYear(), mPerson.getMonth(), mPerson.getDay());

        mDataNascimento.setText(mPerson.getBirthday() != null && mPerson.getBirthday() != "" ?
                        getString(R.string.data_nascimento_formatado, MovieUtils.formateDate(getActivity(), mPerson.getBirthday()), age) + " " + getResources().getQuantityString(R.plurals.number_idade, age) :
                        "--");

        mDescricaoPerson.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "opensans.ttf"));
        mDescricaoPerson.setText(mPerson.getBiography() != null ? mPerson.getBiography() : getString(R.string.nao_ha_biografia));

        mKnowForAdapter = new ListMoviesAdapter(getActivity(), new ArrayList<MovieListDTO>(), mCallbacks, R.layout.item_similares_movie);
        mKnowForRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.HORIZONTAL, false));
        mKnowForRecyclerView.setAdapter(mKnowForAdapter);
        getMovieListDTO();

        int columnCount = getResources().getInteger(R.integer.images_movie_detail_columns);
        mImagensRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));
        mImageAdapter = new ImageAdapter(getActivity(), new ArrayList<ImageDTO>(), mImagesCallbacks);
        mImagensRecyclerView.setAdapter(mImageAdapter);
        getImageDTO();
    }

    private List<ImageDTO> getImageDTO() {
        List<ImageDTO> imageDTOs = new ArrayList<>();
        int numTotalImages = 0;

        if (!mPerson.getTaggedImages().isEmpty()) {
            numTotalImages = mPerson.getTaggedImages().size() < 12 ? mPerson.getTaggedImages().size(): 12;

            for (int cont = 0; cont < numTotalImages; cont++) {
                Artwork image = mPerson.getTaggedImages().get(cont);
                imageDTOs.add(new ImageDTO(mPerson.getId(), image.getId(), image.getFilePath()));
            }
        } else {
            numTotalImages = mPerson.getTaggedImages().size() < 12 ? mPerson.getImages().size(): 12;

            for (int cont = 0; cont < numTotalImages; cont++) {
                Artwork image = mPerson.getImages().get(cont);
                imageDTOs.add(new ImageDTO(mPerson.getId(), image.getId(), image.getFilePath()));
            }
        }

        if (imageDTOs.isEmpty())
            mImagesTitleContainer.setVisibility(View.GONE);

        mImageAdapter.setImages(imageDTOs);
        mImageAdapter.notifyDataSetChanged();

        return imageDTOs;

    }

    private List<MovieListDTO> getMovieListDTO() {
        List<MovieListDTO> listDTOs = new ArrayList<>();

        if (mPerson.getMovieCredits().getCast().size() > 0)
            mAreasAtuacao.add(mPerson.getGender() == Gender.MALE ? "Actor" : "Actress");

        setPersonDTO(mPerson.getMovieCredits().getCast(), listDTOs);
        setPersonDTO(mPerson.getMovieCredits().getCrew(), listDTOs);

        mKnowForAdapter.setList(listDTOs);
        mKnowForAdapter.notifyDataSetChanged();

        return listDTOs;
    }


    private void setPersonDTO(List<CreditMovieBasic> persons, List<MovieListDTO> listDTOs) {
        int numMovies = persons.size() < 10 ? persons.size() : 10;

        for (int cont = 0; cont < numMovies; cont++) {
            CreditMovieBasic person = persons.get(cont);
            listDTOs.add(new MovieListDTO(person.getId(), person.getArtworkPath(), null));

            if (!mAreasAtuacao.contains(person.getDepartment()) && person.getDepartment() != null) {
                mAreasAtuacao.add(person.getDepartment());
            }
        }

        mPrincipaisAreas.setText(MovieUtils.formatList(mAreasAtuacao));
    }


}
