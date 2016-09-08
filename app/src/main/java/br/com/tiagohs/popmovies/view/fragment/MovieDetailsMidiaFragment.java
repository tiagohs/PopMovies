package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
import br.com.tiagohs.popmovies.model.response.VideosResponse;
import br.com.tiagohs.popmovies.presenter.MovieDetailsMidiaPresenter;
import br.com.tiagohs.popmovies.view.MovieDetailsMidiaView;
import br.com.tiagohs.popmovies.view.adapters.ImageAdapter;
import br.com.tiagohs.popmovies.view.adapters.VideoAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ImagesCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.MovieVideosCallbacks;
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

    private MovieVideosCallbacks mVideosCallbacks;
    private ImagesCallbacks mImagesCallbacks;

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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mVideosCallbacks = (MovieVideosCallbacks) context;
        mImagesCallbacks = (ImagesCallbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mVideosCallbacks = null;
        mImagesCallbacks = null;
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
        setupVideoAdapter();

        int columnCount = getResources().getInteger(R.integer.images_movie_detail_columns);
        mImagesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));
        setupImageAdapter();
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_movie_detail_midia;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    public void updateImageUI(ImageResponse imageResponse) {
        mMovieDetails.setImages(imageResponse);
        setupImageAdapter();
    }

    @Override
    public void updateVideoUI(VideosResponse videosResponse) {

        mMovieDetails.setVideos(videosResponse);
        setupVideoAdapter();
    }

    private void setupVideoAdapter() {

        if (mVideoAdapter == null) {
            mVideoAdapter = new VideoAdapter(getActivity(), mMovieDetails.getVideos(), mVideosCallbacks);
            mVideosRecyclerView.setAdapter(mVideoAdapter);
        } else {
            mVideoAdapter.setVideos(mMovieDetails.getVideos());
            mVideoAdapter.notifyDataSetChanged();
        }
    }

    private void setupImageAdapter() {

        if (mMovieDetails.getImages().size() > 6)
            mImageAdapter = new ImageAdapter(getActivity(), getImageDTO(6), mImagesCallbacks);
        else
            mImageAdapter = new ImageAdapter(getActivity(), getImageDTO(mMovieDetails.getImages().size()), mImagesCallbacks);

        mImagesRecyclerView.setAdapter(mImageAdapter);

    }

    private List<ImageDTO> getImageDTO(int numImages) {
        List<ImageDTO> imageDTOs = new ArrayList<>();

        for (int cont = 0; cont < numImages; cont++) {
            Artwork image = mMovieDetails.getImages().get(cont);
            imageDTOs.add(new ImageDTO(mMovieDetails.getId(), image.getId(), image.getFilePath()));
        }

        return imageDTOs;

    }


}
