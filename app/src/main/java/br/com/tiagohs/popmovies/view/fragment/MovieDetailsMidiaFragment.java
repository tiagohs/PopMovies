package br.com.tiagohs.popmovies.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.balysv.materialripple.MaterialRippleLayout;

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
import br.com.tiagohs.popmovies.view.activity.WallpapersActivity;
import br.com.tiagohs.popmovies.view.adapters.ImageAdapter;
import br.com.tiagohs.popmovies.view.adapters.VideoAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ImagesCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.MovieVideosCallbacks;
import butterknife.BindView;
import butterknife.OnClick;

public class MovieDetailsMidiaFragment extends BaseFragment implements MovieDetailsMidiaView {
    private static final String TAG = MovieDetailsMidiaFragment.class.getSimpleName();
    private static final String ARG_MOVIE = "movie";
    private static int NUM_VIDEOS;

    @BindView(R.id.list_videos_recycler_view)
    RecyclerView mVideosRecyclerView;

    @BindView(R.id.images_recycler_view)
    RecyclerView mImagesRecyclerView;

    @BindView(R.id.wallpapers_riple)
    MaterialRippleLayout mWallpapersRiple;

    @Inject
    MovieDetailsMidiaPresenter mPresenter;

    private MovieVideosCallbacks mVideosCallbacks;
    private ImagesCallbacks mImagesCallbacks;

    private VideoAdapter mVideoAdapter;
    private ImageAdapter mImageAdapter;
    private MovieDetails mMovieDetails;
    private List<ImageDTO> mTotalImages;

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
        int columnCount = getResources().getInteger(R.integer.images_movie_detail_columns);
        mImagesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));
        setupImageAdapter();
    }

    @Override
    public void updateVideoUI(VideosResponse videosResponse) {

        mMovieDetails.addMoreVideos(videosResponse.getVideos());
        mVideosRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mVideoAdapter = new VideoAdapter(getActivity(), mMovieDetails.getVideos(), mVideosCallbacks);
        mVideosRecyclerView.setAdapter(mVideoAdapter);
    }

    private void setupImageAdapter() {
        mTotalImages = getImageDTO(mMovieDetails.getImages().size());

        if (mMovieDetails.getImages().size() > 6)
            mImageAdapter = new ImageAdapter(getActivity(), getImageDTO(6), mImagesCallbacks, mTotalImages);
        else
            mImageAdapter = new ImageAdapter(getActivity(), mTotalImages, mImagesCallbacks, mTotalImages);

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

    @OnClick(R.id.wallpapers_riple)
    public void onClickWallpapersTitle() {
        if (!mTotalImages.isEmpty())
            startActivity(WallpapersActivity.newIntent(getActivity(), mTotalImages));
    }

}
