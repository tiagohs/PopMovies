package br.com.tiagohs.popmovies.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMoviesAdapter extends RecyclerView.Adapter<ListMoviesAdapter.ListMoviesViewHolder> {
    private List<MovieListDTO> list;
    private Context mContext;
    private ListMoviesCallbacks mCallbacks;
    private int mLayoutMovieResID;

    public ListMoviesAdapter(Context context, List<MovieListDTO> list, ListMoviesCallbacks callbacks, int layoutMovieResID) {
        this.list = list;
        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mLayoutMovieResID = layoutMovieResID;
    }

    public void setList(List<MovieListDTO> list) {
        this.list = list;
    }

    @Override
    public ListMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(mLayoutMovieResID, parent, false);

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

        @BindView(R.id.nota_progress)
        ProgressBar mProgRanking;

        @BindView(R.id.rodape_list_movies)
        LinearLayout mRodapeListMovies;

        private Context mContext;
        private MovieListDTO mMovie;

        public ListMoviesViewHolder(final Context context, View itemView) {
            super(itemView);
            mContext = context;
            itemView.setOnClickListener(this);

            ButterKnife.bind(this, itemView);
        }

        public void bindMovie(MovieListDTO movie) {
            mMovie = movie;

            ImageUtils.load(mContext, movie.getPosterPath(), mImageView, mMovie.getMovieName(), ImageSize.POSTER_185, mRodapeListMovies);

            if (hasRanking()) {
                Float ranking = Float.parseFloat(mMovie.getVoteAverage());
                mRanking.setText(String.format("%.1f", ranking));

                if (ranking < 5f)
                    setRankingState(R.drawable.progress_circle_red);
                else if (ranking >= 5f && ranking <= 6)
                    setRankingState(R.drawable.progress_circle_yellow);
                else
                    setRankingState(R.drawable.progress_circle_green);

                mProgRanking.setMax(10);
                for (int cont = 0; cont < ranking; cont++) {
                    mProgRanking.setProgress(Math.round(cont));
                }

            } else {
                mProgRanking.setVisibility(View.GONE);
                mRanking.setVisibility(View.GONE);
            }

        }

        private void setRankingState(int state) {
            mProgRanking.setProgressDrawable(ViewUtils.getDrawableFromResource(mContext, state));
        }

        private boolean hasRanking() {
            return mMovie.getVoteAverage() != null;
        }


        @Override
        public void onClick(View view) {
            mCallbacks.onMovieSelected(mMovie.getMovieID(), mImageView);
        }
    }
}
