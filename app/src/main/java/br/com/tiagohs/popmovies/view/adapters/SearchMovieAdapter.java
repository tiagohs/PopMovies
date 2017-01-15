package br.com.tiagohs.popmovies.view.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.movie.Movie;
import br.com.tiagohs.popmovies.presenter.SearchPresenter;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.view.SearchMoviesView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tiago Henrique on 01/09/2016.
 */
public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.SearchViewHolder> {
    private List<Movie> list;
    private Context mContext;
    private SearchMoviesView mView;
    private SearchPresenter mPresenter;

    public SearchMovieAdapter(Context context, List<Movie> list, SearchMoviesView view, SearchPresenter presenter) {
        this.list = list;
        this.mContext = context;
        this.mView = view;
        this.mPresenter = presenter;
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
        @BindView(R.id.poster_movie)                    ImageView mImageView;
        @BindView(R.id.title_movie)                     TextView mTitleMovie;
        @BindView(R.id.movie_ano_lancamento)            TextView mMovieAnoLancamento;
        @BindView(R.id.geners_movie)                    TextView mGeners;
        @BindView(R.id.list_item_movies_progress)       ProgressWheel mProgress;
        @BindView(R.id.movies_ja_assisti)               ImageView mJaAssistiButton;

        private Context mContext;
        private Movie mMovie;
        private boolean mIsWatchPressed;

        public SearchViewHolder(final Context context, View itemView) {
            super(itemView);
            mContext = context;

            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bindMovie(Movie movie) {
            mMovie = movie;
            mIsWatchPressed = false;

            ImageUtils.load(mContext, movie.getPosterPath(), mImageView, R.drawable.placeholder_images_default, R.drawable.placeholder_images_default, ImageSize.LOGO_185, mProgress);

            mTitleMovie.setText(mMovie.getTitle());
            mTitleMovie.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "opensans.ttf"));
            mGeners.setText(MovieUtils.formatGeneres(mContext, mMovie.getGenreIDs()));
            mGeners.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "openSansItalic.ttf"));
            mMovieAnoLancamento.setText(String.valueOf(mMovie.getYearRelease()));
            mMovieAnoLancamento.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "opensans.ttf"));

            if (mPresenter.isJaAssistido(movie.getId())) {
                mJaAssistiButton.setImageDrawable(ViewUtils.getDrawableFromResource(mContext, R.drawable.ic_assistido_green));
            }

            mJaAssistiButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIsWatchPressed = !mIsWatchPressed;

                    mPresenter.getMovieDetails(mMovie.getId(), mIsWatchPressed, SearchMovieAdapter.class.getSimpleName());

                    if (mIsWatchPressed)
                        updateState(R.drawable.ic_assistido_green, "Marcado como Assistido.");
                    else
                        updateState(R.drawable.ic_assitir_eye_yellow, "Desmarcado como Assistido.");
                }
            });
        }

        private void updateState(int iconID, String msg) {
            mJaAssistiButton.setImageDrawable(ViewUtils.getDrawableFromResource(mContext, iconID));

            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onClick(View view) {
            mView.onMovieSelected(mMovie.getId(), mImageView);
        }

    }
}
