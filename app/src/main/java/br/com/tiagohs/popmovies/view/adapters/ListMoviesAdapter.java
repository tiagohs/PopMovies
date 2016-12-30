package br.com.tiagohs.popmovies.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.data.repository.MovieRepository;
import br.com.tiagohs.popmovies.model.Item;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.presenter.ListMoviesDefaultPresenter;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import br.com.tiagohs.popmovies.view.callbacks.LongClickCallbacks;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMoviesAdapter extends RecyclerView.Adapter<ListMoviesAdapter.ListMoviesViewHolder> {
    private static final String TAG = ListMoviesAdapter.class.getSimpleName();

    private List<MovieListDTO> list;
    private Context mContext;
    private ListMoviesCallbacks mCallbacks;
    private int mLayoutMovieResID;
    ListMoviesDefaultPresenter mPresenter;

    public ListMoviesAdapter(Context context, List<MovieListDTO> list, ListMoviesCallbacks callbacks, int layoutMovieResID, ListMoviesDefaultPresenter presenter) {
        this.list = list;
        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mLayoutMovieResID = layoutMovieResID;
        this.mPresenter = presenter;
    }

    public void setList(List<MovieListDTO> list) {
        this.list = list;
    }

    @Override
    public ListMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(mLayoutMovieResID, parent, false);

        return new ListMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListMoviesViewHolder holder, int position) {
        holder.bindMovie(list.get(position));
     }

    @Override
    public int getItemCount() {
        return list.size();
     }


    class ListMoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, LongClickCallbacks {
        @BindView(R.id.poster_movie)                ImageView mImageView;
        @BindView(R.id.rodape_list_movies)          LinearLayout mRodapeListMovies;
        @BindView(R.id.movie_ja_assistido)          View view;

        private MovieListDTO mMovie;
        private boolean isMovieAssistidoMarked;
        private boolean isMovieFavoritoMarked;

        public ListMoviesViewHolder(View itemView) {
            super(itemView);


            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bindMovie(MovieListDTO movie) {
            mMovie = movie;

            ImageUtils.load(mContext, movie.getPosterPath(), mImageView, mMovie.getMovieName(), ImageSize.POSTER_185, mRodapeListMovies);

            if (mMovie.isJaAssistido())
                view.setBackgroundColor(ViewUtils.getColorFromResource(mContext, android.R.color.holo_green_dark));
            else
                view.setVisibility(View.GONE);

        }

        @Override
        public void onClick(View view) {
            mCallbacks.onMovieSelected(mMovie.getMovieID(), mImageView);
        }

        @Override
        public boolean onLongClick(View v) {
            final View view = ((Activity) mContext).getLayoutInflater().inflate(R.layout.long_click_layout, null);
            RecyclerView list = (RecyclerView) view.findViewById(R.id.list_items_recycler);
            list.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));

            List<Item> listItems = new ArrayList<>();
            listItems.add(new Item("Marcado como Favorito", "Marcar como Favorito", R.drawable.ic_favorite_clicked, R.drawable.ic_favorite_border));
            listItems.add(new Item("Marcado como Assistido", "Marcar como Assistido", R.drawable.ic_assistido_preenxido, R.drawable.ic_assitir_eye_black));

            list.setAdapter(new LongClickOptionsAdapter(mContext, listItems, mMovie.getMovieID(), this));

            new MaterialDialog.Builder(mContext)
                                    .customView(list, true)
                                    .title("Opções")
                                    .positiveText("Ok")
                                    .negativeText("Cancelar")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            Log.i(TAG, "Situação " + isMovieAssistidoMarked + " " + isMovieFavoritoMarked);
                                            mPresenter.getMovieDetails(mMovie.getMovieID(), isMovieAssistidoMarked, isMovieFavoritoMarked, TAG);
                                        }
                                    })
                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        }
                                    })
                                    .onAny(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        }
                                    })
                                    .build()
                                    .show();

            return true;
        }

        @Override
        public void onLongClick(boolean isFavorite, boolean isAssistido) {
            isMovieAssistidoMarked = isAssistido;
            isMovieFavoritoMarked = isFavorite;
        }
    }
}
