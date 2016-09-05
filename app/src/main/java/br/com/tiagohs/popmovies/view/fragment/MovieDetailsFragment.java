package br.com.tiagohs.popmovies.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.view.adapters.MovieDetailsAdapter;
import butterknife.BindView;


public class MovieDetailsFragment extends BaseFragment {
    private static final String ARG_MOVIE = "movie";

    @BindView(R.id.movie_view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    private MovieDetails mMovie;

    @Override
    protected int getViewID() {
        return R.layout.include_movie_detail_bellow;
    }

    public static MovieDetailsFragment newInstance(MovieDetails movie) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MOVIE, movie);

        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        movieDetailsFragment.setArguments(bundle);

        return movieDetailsFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMovie = (MovieDetails) getArguments().getSerializable(ARG_MOVIE);

        mViewPager.setAdapter(new MovieDetailsAdapter(getChildFragmentManager(), mMovie, getResources().getStringArray(R.array.movie_detail_tab_array)));
        mTabLayout.setupWithViewPager(mViewPager);
    }


}
