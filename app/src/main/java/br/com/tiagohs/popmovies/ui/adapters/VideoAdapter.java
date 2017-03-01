package br.com.tiagohs.popmovies.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.ui.callbacks.MovieVideosCallbacks;
import br.com.tiagohs.popmovies.util.ImageUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tiago Henrique on 28/08/2016.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private static final String TAG = VideoAdapter.class.getSimpleName();

    private Context mContext;
    private MovieVideosCallbacks mCallback;
    private List<Video> mVideos;

    public VideoAdapter(Context context, List<Video> videos, MovieVideosCallbacks callbacks) {
        this.mContext = context;
        this.mCallback = callbacks;
        this.mVideos = videos;
    }

    public void setVideos(List<Video> videos) {
        mVideos = videos;
    }


    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_videos_default, parent, false);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.bindVideo(mVideos.get(position));
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imagem_video)            ImageView mThumbnailVideo;
        @BindView(R.id.title_video)            TextView mTitleVideo;
        @BindView(R.id.video_item_card_view)    CardView mVideoCardView;
        @BindView(R.id.videos_image_progress)   ProgressWheel mProgress;

        private Video mVideo;

        public VideoViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            mVideoCardView.setOnClickListener(this);
        }

        public void bindVideo(Video video) {
            this.mVideo = video;

            ImageUtils.load(mContext, mContext.getString(R.string.youtube_link, mVideo.getKey()), R.drawable.placeholder_images_default, R.drawable.video_error,  mThumbnailVideo, mProgress);
            mTitleVideo.setText(mVideo.getName());
        }

        @Override
        public void onClick(View view) {
            mCallback.onClickVideo(mVideo);
        }
    }
}
