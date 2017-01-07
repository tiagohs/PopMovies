package br.com.tiagohs.popmovies.view.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.model.Item;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.view.callbacks.LongClickCallbacks;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tiago on 29/12/2016.
 */

public class LongClickOptionsAdapter  extends RecyclerView.Adapter<LongClickOptionsAdapter.LongClickOptionsViewHolder> {
    private static final String TAG = ListMoviesAdapter.class.getSimpleName();

    private List<Item> mItemList;
    private Context mContext;
    private int mMovieID;
    private long mProfileID;
    private LongClickCallbacks mCallback;

    public LongClickOptionsAdapter(Context context, List<Item> itemList, int movieID, LongClickCallbacks callback) {
        this.mItemList = itemList;
        this.mContext = context;
        this.mMovieID = movieID;
        this.mCallback = callback;
        this.mProfileID = PrefsUtils.getCurrentProfile(mContext).getProfileID();
    }

    @Override
    public LongClickOptionsAdapter.LongClickOptionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_long_click_layout, parent, false);

        return new LongClickOptionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LongClickOptionsAdapter.LongClickOptionsViewHolder holder, int position) {
        holder.bindMovie(position);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class LongClickOptionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_icon)
        ImageView mItemIcon;
        @BindView(R.id.item_text)
        TextView mItemText;
        @BindView(R.id.item_riple)
        MaterialRippleLayout mItemRiple;

        private Item mItem;
        private MovieRepository mMovieRepository;
        private Movie mMovie;
        private int mPosition;
        private boolean mIsFavorite;
        private boolean mIsAssistido;
        private int status;

        public LongClickOptionsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            mMovieRepository = new MovieRepository(mContext);
            mMovie = mMovieRepository.findMovieByServerID(mMovieID, mProfileID);

            if (mMovie != null) {
                mIsAssistido = true;
                mIsFavorite = mMovie.isFavorite();
            }
        }

        public void bindMovie(int position) {
            mItem = mItemList.get(position);
            mPosition = position;

            mItemText.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "opensans.ttf"));

            if (position == 0) {
                updateFavorite();
            } else if (position == 1) {
                updateAsstidos();
            }

            mItemRiple.setOnClickListener(this);

        }

        public void updateFavorite() {
            if (mIsFavorite) {
                mItemIcon.setImageDrawable(ViewUtils.getDrawableFromResource(mContext, mItem.getItemIconMarcado()));
                mItemText.setText(mItem.getItemTextMarcado());
            } else {
                mItemIcon.setImageDrawable(ViewUtils.getDrawableFromResource(mContext, mItem.getItemIconDesmarcado()));
                mItemText.setText(mItem.getItemTextDesmarcado());
            }
        }

        public void updateAsstidos() {
            if (mIsAssistido) {
                mItemIcon.setImageDrawable(ViewUtils.getDrawableFromResource(mContext, mItem.getItemIconMarcado()));
                mItemText.setText(mItem.getItemTextMarcado());
            } else {
                mItemIcon.setImageDrawable(ViewUtils.getDrawableFromResource(mContext, mItem.getItemIconDesmarcado()));
                mItemText.setText(mItem.getItemTextDesmarcado());
            }
        }

        @Override
        public void onClick(View view) {
            if (mPosition == 0) {
                mIsFavorite = !mIsFavorite;
                updateFavorite();
            } else if (mPosition == 1) {
                mIsAssistido = !mIsAssistido;
                status = MovieDB.STATUS_WATCHED;
                updateAsstidos();
            }

            mCallback.onLongClick(mIsFavorite, mIsAssistido, status);
        }

    }
}
