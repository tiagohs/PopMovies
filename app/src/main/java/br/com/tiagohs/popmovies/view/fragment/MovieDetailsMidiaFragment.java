package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.Movie.MovieDetails;
import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.presenter.MovieDetailsMidiaPresenter;
import br.com.tiagohs.popmovies.view.MovieDetailsMidiaView;
import br.com.tiagohs.popmovies.view.adapters.ImageAdapter;
import br.com.tiagohs.popmovies.view.adapters.VideoAdapter;
import butterknife.BindView;

public class MovieDetailsMidiaFragment extends BaseFragment implements MovieDetailsMidiaView {
    private static final String TAG = MovieDetailsMidiaFragment.class.getSimpleName();
    private static final String ARG_MOVIE = "movie";
    private static int NUM_VIDEOS;

    @BindView(R.id.list_videos_recycler_view)
    RecyclerView mVideosRecyclerView;

    @BindView(R.id.images_recycler_view)
    RecyclerView mImagesRecyclerView;

    @Inject
    MovieDetailsMidiaPresenter mPresenter;

    private Callbacks mCallbacks;

    private VideoAdapter mVideoAdapter;
    private ImageAdapter mImageAdapter;
    private MovieDetails mMovieDetails;

    public static MovieDetailsMidiaFragment newInstance(MovieDetails movie) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_MOVIE, movie);

        MovieDetailsMidiaFragment movieDetailsMidiaFragment = new MovieDetailsMidiaFragment();
        movieDetailsMidiaFragment.setArguments(bundle);

        return movieDetailsMidiaFragment;
    }

    public interface Callbacks {
        void onClickVideo(Video video);
        void onClickImage(Artwork image);
    }

    public MovieDetailsMidiaFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getPopMoviesComponent().inject(this);

        mMovieDetails = (MovieDetails) getArguments().getSerializable(ARG_MOVIE);
        mPresenter.setView(this);

        mPresenter.getVideos(mMovieDetails.getId());
        mPresenter.getImagens(mMovieDetails.getId());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mVideosRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        setupMovieAdapter();

        mImagesRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        setupImageAdapter();
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_movie_detail_midia;
    }

    @Override
    public void updateImageUI(ImageResponse imageResponse) {
        mMovieDetails.setImages(imageResponse);
        setupImageAdapter();
    }

    @Override
    public void updateVideoUI(VideosResponse videosResponse) {

        mMovieDetails.setVideos(videosResponse);
        setupMovieAdapter();
    }

    private void setupMovieAdapter() {

        if (mVideoAdapter == null) {
            Log.i(TAG, "Adapter null");
            mVideoAdapter = new VideoAdapter(getActivity(), mMovieDetails.getVideos(), mCallbacks);
            mVideosRecyclerView.setAdapter(mVideoAdapter);
        } else {
            Log.i(TAG, "Adapter não null");
            mVideoAdapter.setVideos(mMovieDetails.getVideos());
            mVideoAdapter.notifyDataSetChanged();
        }
    }

    private void setupImageAdapter() {

        if (mImageAdapter == null) {
            mImageAdapter = new ImageAdapter(getActivity(), mMovieDetails.getImages(), mCallbacks);
            mImagesRecyclerView.setAdapter(mImageAdapter);
        } else {
            mImageAdapter.setImages(mMovieDetails.getImages());
            mImageAdapter.notifyDataSetChanged();
        }
    }


}
