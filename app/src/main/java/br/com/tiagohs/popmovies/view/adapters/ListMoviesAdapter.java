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
import br.com.tiagohs.popmovies.model.Movie.Movie;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.view.fragment.ListMoviesFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tiago Henrique on 01/09/2016.
 */
public class ListMoviesAdapter extends RecyclerView.Adapter<ListMoviesAdapter.ListMoviesViewHolder> {
    private List<Movie> list;
    private Context mContext;
    private ListMoviesFragment.Callbacks mCallbacks;

    public ListMoviesAdapter(Context context, List<Movie> list, ListMoviesFragment.Callbacks callbacks) {
        this.list = list;
        this.mContext = context;
        this.mCallbacks = callbacks;
    }

    @Override
    public ListMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_movies, parent, false);

        return new ListMoviesViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(ListMoviesViewHolder holder, int position) {
        holder.bindMovie(list.get(position));
     }

    @Override
    public int getItemCount() {
        return list.size();
     }


    class ListMoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.poster_movie)
        ImageView mImageView;

        @BindView(R.id.movie_raking_total)
        TextView mRanking;

        @BindView(R.id.list_item_movies_progress)
        ProgressWheel mProgress;

        private Context mContext;
        private Movie mMovie;

        public ListMoviesViewHolder(final Context context, View itemView) {
            super(itemView);
            mContext = context;
            itemView.setOnClickListener(this);

            ButterKnife.bind(this, itemView);
        }

        public void bindMovie(Movie movie) {
            mMovie = movie;

            ImageUtils.load(mContext, movie.getPosterPath(), mImageView, R.drawable.placeholder_images_default, R.drawable.placeholder_images_default, ImageSize.LOGO_185, mProgress);
            mRanking.setText(mMovie.getVoteAverage());
        }

        @Override
        public void onClick(View view) {
            mCallbacks.onMovieSelected(mMovie.getId(), mImageView);
        }
    }
}
