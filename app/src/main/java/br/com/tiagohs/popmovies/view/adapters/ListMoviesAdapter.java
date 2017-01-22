package br.com.tiagohs.popmovies.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.Item;
import br.com.tiagohs.popmovies.model.dto.MovieListDTO;
import br.com.tiagohs.popmovies.presenter.ListMoviesDefaultPresenter;
import br.com.tiagohs.popmovies.util.ImageUtils;
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

        private MovieListDTO mMovie;
        private boolean isToSave;
        private boolean isMovieFavoritoMarked;
        private int mStatus;

        public ListMoviesViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bindMovie(MovieListDTO movie) {
            mMovie = movie;

            ImageUtils.load(mContext, movie.getPosterPath(), mImageView, mMovie.getMovieName(), ImageSize.POSTER_185, mRodapeListMovies);
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
            listItems.add(new Item("Marcado como Favorito", "Marcar como Favorito", R.drawable.ic_long_click_favorite_clicked, R.drawable.ic_long_click_favorite_normal, android.R.color.holo_red_dark));
            listItems.add(new Item("Marcado como Assistido", "Marcar como Assistido", R.drawable.ic_long_click_assistido_clicked, R.drawable.ic_long_click_assistido_normal, android.R.color.holo_green_dark));
            listItems.add(new Item("Marcado como Quero ver", "Marcar como Quero ver", R.drawable.ic_long_click_quero_ver_clicked, R.drawable.ic_long_click_quero_ver_normal, R.color.yellow));
            listItems.add(new Item("Marcado como Não Quero ver", "Marcar como Não Quero ver", R.drawable.ic_long_click_nao_quero_ver_clicked, R.drawable.ic_long_click_nao_quero_ver_normal, R.color.colorAccent));

            list.setAdapter(new LongClickOptionsAdapter(mContext, listItems, mMovie.getMovieID(), this));

            new MaterialDialog.Builder(mContext)
                                    .customView(list, true)
                                    .title("Opções")
                                    .positiveText("Ok")
                                    .negativeText("Cancelar")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            mPresenter.getMovieDetails(mMovie.getMovieID(), isToSave, isMovieFavoritoMarked, mStatus, TAG);
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
        public void onLongClick(boolean isFavorite, boolean isToSave, int status) {
            mStatus = status;
            this.isToSave = isToSave;
            isMovieFavoritoMarked = isFavorite;
        }
    }
}
