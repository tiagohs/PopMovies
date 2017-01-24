package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.pnikosis.materialishprogress.ProgressWheel;

import org.apache.commons.collections4.ListUtils;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;
import br.com.tiagohs.popmovies.model.dto.CarrerMoviesDTO;
import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.enumerations.CreditType;
import br.com.tiagohs.popmovies.util.enumerations.MediaType;
import br.com.tiagohs.popmovies.view.adapters.CarrerMoviesAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import butterknife.BindView;

public class PersonDetailCarrerFragment extends BaseFragment {
    private static final String TAG = PersonDetailCarrerFragment.class.getSimpleName();

    private static final String ARG_PERSON = "person";

    @BindView(R.id.list_person_movies_recycler_view)        RecyclerView mPersonMoviesRecyclerView;
    @BindView(R.id.principal_progress)                      ProgressWheel mProgress;

    private PersonInfo mPersonInfo;
    private ListMoviesCallbacks mCallbacks;
    private CarrerMoviesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<CarrerMoviesDTO> mCarrerList;

    private List<List<CarrerMoviesDTO>> mMoviesSubList;
    private int mIndexList = 0;
    private boolean hasMorePages;
    private int totalPage;

    private boolean mIsCarrerLoaded = false;

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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !mIsCarrerLoaded ) {
            initUpdates();
            mIsCarrerLoaded = true;
        }
    }

    private void initUpdates() {
        mCarrerList = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        mPersonMoviesRecyclerView.setLayoutManager(mLayoutManager);
        mPersonMoviesRecyclerView.addOnScrollListener(createOnScrollListener());
        mAdapter = new CarrerMoviesAdapter(getActivity(), this, mCarrerList, mCallbacks, R.layout.item_person_detail_movies);
        mPersonMoviesRecyclerView.setAdapter(mAdapter);

        new CreateDTO().execute();
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_person_detail_carrer;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSnackbar.dismiss();
            }
        };
    }

    private RecyclerView.OnScrollListener createOnScrollListener() {
        return new EndlessRecyclerView(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (hasMorePages)
                    addMoreMovies();
            }
        };
    }

    private void addMoreMovies() {
        mCarrerList.addAll(mMoviesSubList.get(mIndexList++));
        hasMorePages = mIndexList < (totalPage - 1);
        mAdapter.notifyDataSetChanged();
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
                if (g.getMediaType().equals(MediaType.MOVIE)) {
                        list.add(new CarrerMoviesDTO(g.getId(), g.getTitle(), g.getOriginalTitle(), g.getArtworkPath(), g.getReleaseDate(), g.getCharacter(), g.getDepartment(), g.getCreditType()));
                }
        }

        @Override
        protected void onPostExecute(List<CarrerMoviesDTO> carrerMoviesDTOs) {
            super.onPostExecute(carrerMoviesDTOs);

            mMoviesSubList = ListUtils.partition(carrerMoviesDTOs, 20);
            totalPage = mMoviesSubList.size();
            hasMorePages = mIndexList < (totalPage - 1);
            mCarrerList.addAll(mMoviesSubList.get(mIndexList++));
            mPersonMoviesRecyclerView.setAdapter(mAdapter);
            mProgress.setVisibility(View.GONE);
        }
    }
}
