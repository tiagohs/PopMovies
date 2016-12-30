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
import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.model.movie.MovieDetails;
import br.com.tiagohs.popmovies.model.response.ImageResponse;
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

    private static final String ARG_MOVIE_SAVED = "moviesSaved";
    private static final String ARG_IS_VIDEO_SEARCHED = "isVideoSerched";
    private static final String ARG_IMAGES_SAVED = "imagesSaved";

    @BindView(R.id.list_videos_recycler_view)       RecyclerView mVideosRecyclerView;
    @BindView(R.id.images_recycler_view)            RecyclerView mImagesRecyclerView;
    @BindView(R.id.wallpapers_riple)                MaterialRippleLayout mWallpapersRiple;

    @Inject
    MovieDetailsMidiaPresenter mPresenter;

    private MovieVideosCallbacks mVideosCallbacks;
    private ImagesCallbacks mImagesCallbacks;

    private VideoAdapter mVideoAdapter;
    private ImageAdapter mImageAdapter;
    private MovieDetails mMovieDetails;
    private List<ImageDTO> mTotalImages;

    private boolean isVideosSearched = false;

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

        mPresenter.setView(this);

        if (savedInstanceState != null) {
            isVideosSearched = savedInstanceState.getBoolean(ARG_IS_VIDEO_SEARCHED);
            mTotalImages = (ArrayList<ImageDTO>) savedInstanceState.getSerializable(ARG_IMAGES_SAVED);
            mMovieDetails = (MovieDetails) savedInstanceState.getSerializable(ARG_MOVIE_SAVED);
        } else {
            mMovieDetails = (MovieDetails) getArguments().getSerializable(ARG_MOVIE);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mPresenter != null)
            mPresenter.onCancellRequest(getActivity(), TAG);
    }

    private void init() {
        if (isVideosSearched)
            updateVideoUI(mMovieDetails.getVideos());
        else
            mPresenter.getVideos(mMovieDetails.getId(), TAG, mMovieDetails.getOriginalLanguage());

        if (mTotalImages != null)
            setupImageAdapter();
        else
            mPresenter.getImagens(mMovieDetails.getId(), TAG);
    }

    public void setVideosSearched(boolean videosSearched) {
        isVideosSearched = videosSearched;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (isVideosSearched)
            outState.putBoolean(ARG_IS_VIDEO_SEARCHED, isVideosSearched);
        if (mTotalImages != null)
            outState.putSerializable(ARG_IMAGES_SAVED, (ArrayList<ImageDTO>) mTotalImages);
        if (mMovieDetails != null)
            outState.putSerializable(ARG_MOVIE_SAVED, mMovieDetails);
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
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
                mSnackbar.dismiss();
            }
        };
    }

    @Override
    public void updateImageUI(ImageResponse imageResponse) {
        mMovieDetails.setImages(imageResponse);
        int columnCount = getResources().getInteger(R.integer.images_movie_detail_columns);
        mImagesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), columnCount));
        mImagesRecyclerView.setNestedScrollingEnabled(false);
        mTotalImages = getImageDTO(mMovieDetails.getImages().size());
        setupImageAdapter();
    }

    @Override
    public void updateVideoUI(List<Video> videosResponse) {

        mMovieDetails.addMoreVideos(videosResponse);
        mVideosRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mVideosRecyclerView.setNestedScrollingEnabled(false);
        mVideoAdapter = new VideoAdapter(getActivity(), mMovieDetails.getVideos(), mVideosCallbacks);
        mVideosRecyclerView.setAdapter(mVideoAdapter);
    }

    private void setupImageAdapter() {

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
            startActivity(WallpapersActivity.newIntent(getActivity(), mTotalImages, getString(R.string.wallpapers_title), mMovieDetails.getTitle()));
    }

    @Override
    public void setProgressVisibility(int visibityState) {

    }
}
