package br.com.tiagohs.popmovies.view.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.media.Video;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsMidiaFragment;

/**
 * Created by Tiago Henrique on 28/08/2016.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private static final String TAG = VideoAdapter.class.getSimpleName();

    private Context mContext;
    private MovieDetailsMidiaFragment.Callbacks mCallback;
    private List<Video> mVideos;

    public VideoAdapter(Context context, List<Video> videos, MovieDetailsMidiaFragment.Callbacks callbacks) {
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
        View view = layoutInflater.inflate(R.layout.videos_item_movie, parent, false);

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
        private Video mVideo;
        private ImageView mThumbnailVideo;
        private TextView mTitleVideo;
        private CardView mVideoCardView;

        public VideoViewHolder(View itemView) {
            super(itemView);

            mThumbnailVideo = (ImageView) itemView.findViewById(R.id.imagem_video);
            mTitleVideo = (TextView) itemView.findViewById(R.id.title_video);
            mVideoCardView = (CardView) itemView.findViewById(R.id.video_item_card_view);

            mVideoCardView.setOnClickListener(this);
        }

        public void bindVideo(Video video) {
            this.mVideo = video;

            ImageUtils.load(mContext, "http://img.youtube.com/vi/" + mVideo.getKey() + "/0.jpg", mThumbnailVideo);
            mTitleVideo.setText(mVideo.getName());
        }

        @Override
        public void onClick(View view) {
            mCallback.onClickVideo(mVideo);
        }
    }
}
