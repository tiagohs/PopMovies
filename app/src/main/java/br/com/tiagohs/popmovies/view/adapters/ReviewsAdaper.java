package br.com.tiagohs.popmovies.view.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.review.Review;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.view.callbacks.ReviewCallbacks;
import br.com.tiagohs.popmovies.view.fragment.TextViewExpandableAnimation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReviewsAdaper extends RecyclerView.Adapter<ReviewsAdaper.ReviewsViewHolder> {
    private Context mContext;
    private List<Review> mReviews;
    private ReviewCallbacks mCallbacks;

    public ReviewsAdaper(Context context, List<Review> reviews, ReviewCallbacks callbacks) {
        this.mContext = context;
        this.mReviews = reviews;
        this.mCallbacks = callbacks;
    }

    public void setReviews(List<Review> reviews) {
        mReviews = reviews;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_review, parent, false);

        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        holder.bindReview(mReviews.get(position));
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    class ReviewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imagem_author)           ImageView mAuthorReviewImage;
        @BindView(R.id.review_author_name)      TextView mAuthorName;
        @BindView(R.id.review_content)          TextViewExpandableAnimation mReviewContent;

        private Review mReview;

        public ReviewsViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);

            mAuthorName.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "opensans.ttf"));
        }

        public void bindReview(Review review) {
            this.mReview = review;

            mAuthorName.setText(review.getAuthor());
            mReviewContent.setText(review.getContent());

            ImageUtils.loadByCircularImage(mContext, "", mAuthorReviewImage, review.getAuthor(), ImageSize.PROFILE_185);
        }

        @OnClick({R.id.review_author_name, R.id.imagem_author})
        public void onClickAuthorName() {
            mCallbacks.onClickReviewLink(mReview.getUrl());
        }

        @Override
        public void onClick(View view) {
            mCallbacks.onClickReview(mReview.getId());
        }
    }
}
