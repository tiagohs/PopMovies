package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;
import br.com.tiagohs.popmovies.model.dto.CarrerMoviesDTO;
import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.enumerations.MediaType;
import br.com.tiagohs.popmovies.view.adapters.CarrerMoviesAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import butterknife.BindView;

public class PersonDetailCarrerFragment extends BaseFragment {
    private static final String ARG_PERSON = "person";

    @BindView(R.id.list_person_movies_recycler_view)        RecyclerView mPersonMoviesRecyclerView;

    private PersonInfo mPersonInfo;
    private ListMoviesCallbacks mCallbacks;
    private CarrerMoviesAdapter mAdapter;

    public static PersonDetailCarrerFragment newInstance(PersonInfo personInfo) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_PERSON, personInfo);

        PersonDetailCarrerFragment personDetailCarrerFragment = new PersonDetailCarrerFragment();
        personDetailCarrerFragment.setArguments(bundle);
        return personDetailCarrerFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (ListMoviesCallbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPersonInfo = (PersonInfo) getArguments().getSerializable(ARG_PERSON);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<CarrerMoviesDTO> l = new ArrayList<>();
        mPersonMoviesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CarrerMoviesAdapter(getActivity(), this, l, mCallbacks, R.layout.item_person_detail_movies);
        mPersonMoviesRecyclerView.setAdapter(mAdapter);
        new CreateDTO().execute();
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_person_detail_carrer;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    private class CreateDTO extends AsyncTask<Void, Void, List<CarrerMoviesDTO>> {

        @Override
        protected List<CarrerMoviesDTO> doInBackground(Void... voids) {
            List<CarrerMoviesDTO> list = new ArrayList<>();

            createDTO(mPersonInfo.getCastCombined(), list);
            createDTO(mPersonInfo.getCrewCombined(), list);
            MovieUtils.sortMoviesByDate(list);

            return list;
        }

        private void createDTO(List<CreditMovieBasic> dto, List<CarrerMoviesDTO> list) {
            for (CreditMovieBasic g : dto)
                if (g.getMediaType().equals(MediaType.MOVIE))
                    list.add(new CarrerMoviesDTO(g.getId(), g.getTitle(), g.getOriginalTitle(), g.getArtworkPath(), g.getReleaseDate(), g.getCharacter(), g.getDepartment(), g.getCreditType()));
        }

        @Override
        protected void onPostExecute(List<CarrerMoviesDTO> carrerMoviesDTOs) {
            super.onPostExecute(carrerMoviesDTOs);
            mAdapter.setList(carrerMoviesDTOs);
            mAdapter.notifyDataSetChanged();
        }
    }
}
