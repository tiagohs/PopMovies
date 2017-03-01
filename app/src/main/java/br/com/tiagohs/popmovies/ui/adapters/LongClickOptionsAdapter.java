package br.com.tiagohs.popmovies.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.Item;
import br.com.tiagohs.popmovies.model.db.MovieDB;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.ui.callbacks.LongClickCallbacks;
import br.com.tiagohs.popmovies.ui.contracts.ListMoviesDefaultContract;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LongClickOptionsAdapter  extends RecyclerView.Adapter<LongClickOptionsAdapter.LongClickOptionsViewHolder> {
    private static final String TAG = ListMoviesAdapter.class.getSimpleName();

    private static final int OPT_FAVORITE = 0;
    private static final int OPT_WATCHED = 1;
    private static final int OPT_WANT_SEE = 2;
    private static final int OPT_DONT_WANT_SEE = 3;

    private List<Item> mItemList;
    private Context mContext;
    private int mMovieID;
    private LongClickCallbacks mCallback;

    private ListMoviesDefaultContract.ListMoviesDefaultPresenter mPresenter;

    private boolean mIsFavorite;
    private boolean mDontIsFavorite;
    private boolean mIsToSave;
    private boolean mIsWatched;
    private boolean mIsWantSee;
    private boolean mIsDontWantSee;

    public LongClickOptionsAdapter(Context context, List<Item> itemList, int movieID, ListMoviesDefaultContract.ListMoviesDefaultPresenter presenter, LongClickCallbacks callback) {
        this.mItemList = itemList;
        this.mContext = context;
        this.mMovieID = movieID;
        this.mCallback = callback;
        this.mPresenter = presenter;

        configureOptions();
    }

    private void configureOptions() {
        mPresenter.isJaAssistido(mMovieID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Movie>() {
                    @Override
                    public void accept(Movie movie) throws Exception {
                        if (EmptyUtils.isNotNull(movie)) {

                            if (movie.getStatusDB() == MovieDB.STATUS_WATCHED) {
                                mIsToSave = mIsWatched = true;
                            } else if (movie.getStatusDB() == MovieDB.STATUS_WANT_SEE) {
                                mIsToSave = mIsWantSee = true;
                            } else if (movie.getStatusDB() == MovieDB.STATUS_DONT_WANT_SEE) {
                                mIsToSave = mIsDontWantSee = true;
                            }

                            mIsFavorite = movie.isFavorite();
                            notifyDataSetChanged();
                        }
                    }
                });

    }

    @Override
    public LongClickOptionsAdapter.LongClickOptionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_long_click_layout, parent, false);



        return new LongClickOptionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LongClickOptionsAdapter.LongClickOptionsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class LongClickOptionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_icon)       ImageView mItemIcon;
        @BindView(R.id.item_text)       TextView mItemText;
        @BindView(R.id.item_riple)      MaterialRippleLayout mItemRiple;

        private Item mItem;
        private int mPosition;
        private int mStatus;

        public LongClickOptionsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            mItemRiple.setOnClickListener(this);

        }

        public void bind(int position) {
            mItem = mItemList.get(position);
            mPosition = position;

            bindButton();
        }

        private void bindButton() {
            switch (mPosition) {
                case OPT_FAVORITE:
                    update(mIsFavorite);
                    break;
                case OPT_WATCHED:
                    update(mIsWatched);
                    break;
                case OPT_WANT_SEE:
                    update(mIsWantSee);
                    break;
                case OPT_DONT_WANT_SEE:
                    update(mIsDontWantSee);
                    break;
            }
        }

        public void update(boolean isButtonClicked) {

            if (isButtonClicked) {
                mItemIcon.setImageDrawable(ViewUtils.getDrawableFromResource(mContext, mItem.getItemIconMarcado()));
                mItemText.setText(mItem.getItemTextMarcado());
                mItemText.setTextColor(ViewUtils.getColorFromResource(mContext, mItem.getColorID()));
            } else {
                mItemIcon.setImageDrawable(ViewUtils.getDrawableFromResource(mContext, mItem.getItemIconDesmarcado()));
                mItemText.setText(mItem.getItemTextDesmarcado());
                mItemText.setTextColor(ViewUtils.getColorFromResource(mContext, R.color.secondary_text));
            }
        }

        @Override
        public void onClick(View view) {
            switch (mPosition) {
                case OPT_FAVORITE:
                    onClickFavorite();
                    break;
                case OPT_WATCHED:
                    onClickWatched();
                    break;
                case OPT_WANT_SEE:
                    onClickWantSee();
                    break;
                case OPT_DONT_WANT_SEE:
                    onClickDontWantSee();
                    break;
            }

            mCallback.onLongClick(mIsFavorite, mIsToSave, mStatus, mDontIsFavorite);
        }

        private void onClickFavorite() {
            mIsFavorite = !mIsFavorite;

            update(mIsFavorite);

            if (!mIsFavorite && mIsWatched) {
                mDontIsFavorite = true;
                mIsToSave = true;
            }


            if (!mIsWatched)
                mIsWatched = true;

            if (mIsDontWantSee)
                mIsDontWantSee = false;

            if (mIsWantSee)
                mIsWantSee = false;

            notifyDataSetChanged();
        }

        private void onClickWatched() {
            mIsWatched = !mIsWatched;
            mIsToSave = mIsWatched;
            mStatus = MovieDB.STATUS_WATCHED;
            update(mIsWatched);

            if ((!mIsWatched) && mIsFavorite) {
                mIsFavorite = false;
            }

            if (mIsDontWantSee)
                mIsDontWantSee = false;

            if (mIsWantSee)
                mIsWantSee = false;

            notifyDataSetChanged();
        }

        private void onClickWantSee() {
            mIsWantSee = !mIsWantSee;
            mIsToSave = mIsWantSee;
            mStatus = MovieDB.STATUS_WANT_SEE;
            update(mIsWantSee);

            if (mIsDontWantSee)
                mIsDontWantSee = false;

            if (mIsWatched)
                mIsWatched = false;

            if (mIsFavorite)
                mIsFavorite = false;

            notifyDataSetChanged();
        }

        private void onClickDontWantSee() {
            mIsDontWantSee = !mIsDontWantSee;
            mIsToSave = mIsDontWantSee;
            mStatus = MovieDB.STATUS_DONT_WANT_SEE;
            update(mIsDontWantSee);

            if (mIsWantSee)
                mIsWantSee = false;

            if (mIsWatched)
                mIsWatched = false;

            if (mIsFavorite)
                mIsFavorite = false;

            notifyDataSetChanged();
        }

    }
}
