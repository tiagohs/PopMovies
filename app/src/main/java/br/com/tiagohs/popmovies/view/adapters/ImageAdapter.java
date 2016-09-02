package br.com.tiagohs.popmovies.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.atwork.Artwork;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsMidiaFragment;

/**
 * Created by Tiago Henrique on 28/08/2016.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<Artwork> mImages;
    private Context mContext;
    private MovieDetailsMidiaFragment.Callbacks mCallbacks;

    public ImageAdapter(Context context, List<Artwork> images, MovieDetailsMidiaFragment.Callbacks callbacks) {
        this.mContext = context;
        this.mImages = images;
        this.mCallbacks = callbacks;
    }

    public void setImages(List<Artwork> images) {
        mImages = images;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_wallpapers_movies, parent, false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.bindImage(mImages.get(position));
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private Artwork mImage;
        private TextView mRaking;
        private TextView mTotalVotos;
        private ImageView mImageArtwork;
        private ProgressWheel mProgress;

        public ImageViewHolder(View itemView) {
            super(itemView);

            mRaking = (TextView) itemView.findViewById(R.id.image_movie_raking_total);
            mTotalVotos = (TextView) itemView.findViewById(R.id.image_movie_votes);
            mImageArtwork = (ImageView) itemView.findViewById(R.id.image_movie_poster_movie);
            mProgress = (ProgressWheel) itemView.findViewById(R.id.image_item_movie_progress);
        }

        public void bindImage(Artwork image) {
            this.mImage = image;

            mRaking.setText(String.format("%.1f", mImage.getVoteAverage()));
            mTotalVotos.setText(MovieUtils.formatAbrev((long) mImage.getVoteCount()));
            ImageUtils.load(mContext, mImage.getFilePath(), mImageArtwork, R.drawable.placeholder_images_default, R.drawable.placeholder_images_default, ImageSize.BACKDROP_300, mProgress);
        }
    }


}
