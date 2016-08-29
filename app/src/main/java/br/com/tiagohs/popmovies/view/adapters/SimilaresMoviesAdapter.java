package br.com.tiagohs.popmovies.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.Movie.Movie;
import br.com.tiagohs.popmovies.model.Movie.MovieDetails;
import br.com.tiagohs.popmovies.util.ImageSize;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsOverviewFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SimilaresMoviesAdapter extends RecyclerView.Adapter<SimilaresMoviesAdapter.SimilaresMoviesViewHolder> {
    private List<MovieDetails> list;
    private Context mContext;
    private MovieDetailsOverviewFragment.Callbacks mCallbacks;

    public SimilaresMoviesAdapter(Context context, List<MovieDetails> list, MovieDetailsOverviewFragment.Callbacks callbacks) {
        this.list = list;
        this.mContext = context;
        this.mCallbacks = callbacks;
    }

    @Override
    public SimilaresMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.similares_item_movie, parent, false);

        return new SimilaresMoviesViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(SimilaresMoviesViewHolder holder, int position) {
        holder.bindMovie(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class SimilaresMoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.poster_movie)
        ImageView mImageView;

        @BindView(R.id.movie_raking_total)
        TextView mRanking;

        private Context mContext;
        private Movie mMovie;

        public SimilaresMoviesViewHolder(final Context context, View itemView) {
            super(itemView);
            mContext = context;
            itemView.setOnClickListener(this);

            ButterKnife.bind(this, itemView);
        }

        public void bindMovie(Movie movie) {
            mMovie = movie;

            ImageUtils.load(mContext, movie.getPosterPath(), mImageView, ImageSize.LOGO_185);
            mRanking.setText(mMovie.getVoteAverage());
        }

        @Override
        public void onClick(View view) {
            mCallbacks.onMovieSelected(mMovie.getId(), mImageView);
        }
    }

}
