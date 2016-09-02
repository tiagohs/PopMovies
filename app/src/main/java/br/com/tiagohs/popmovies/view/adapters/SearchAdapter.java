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
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.view.SearchView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tiago Henrique on 01/09/2016.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private List<Movie> list;
    private Context mContext;
    private SearchView mView;

    public SearchAdapter(Context context, List<Movie> list, SearchView view) {
        this.list = list;
        this.mContext = context;
        this.mView = view;
    }

    public void setList(List<Movie> list) {
        this.list = list;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_search_movie, parent, false);

        return new SearchViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        holder.bindMovie(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.poster_movie)
        ImageView mImageView;

        @BindView(R.id.movie_raking_total)
        TextView mRanking;

        @BindView(R.id.title_movie)
        TextView mTitleMovie;

        @BindView(R.id.movie_votes)
        TextView mMovieVotes;

        @BindView(R.id.movie_ano_lancamento)
        TextView mMovieAnoLancamento;

        @BindView(R.id.geners_movie)
        TextView mGeners;

        @BindView(R.id.list_item_movies_progress)
        ProgressWheel mProgress;

        private Context mContext;
        private Movie mMovie;

        public SearchViewHolder(final Context context, View itemView) {
            super(itemView);
            mContext = context;
            itemView.setOnClickListener(this);

            ButterKnife.bind(this, itemView);
        }

        public void bindMovie(Movie movie) {
            mMovie = movie;

            ImageUtils.load(mContext, movie.getPosterPath(), mImageView, R.drawable.placeholder_images_default, R.drawable.placeholder_images_default, ImageSize.LOGO_185, mProgress);
            mRanking.setText(mMovie.getVoteAverage());
            mTitleMovie.setText(mMovie.getTitle());
            mMovieVotes.setText(MovieUtils.formatAbrev(mMovie.getVoteCount()));
            mGeners.setText(MovieUtils.formatGeneres(mContext, mMovie.getGenreIDs()));
            mMovieAnoLancamento.setText(String.valueOf(mMovie.getYearRelease()));
        }

        @Override
        public void onClick(View view) {
            mView.onMovieSelected(mMovie.getId(), mImageView);
        }
    }
}
