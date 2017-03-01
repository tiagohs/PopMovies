package br.com.tiagohs.popmovies.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.ui.callbacks.GenresCallbacks;
import br.com.tiagohs.popmovies.util.ImageUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GenresListAdapter extends RecyclerView.Adapter<GenresListAdapter.GenresActivityViewHolder> {

    private Context mContext;
    private GenresCallbacks mCallback;
    private List<Genre> mGeneros;

    public GenresListAdapter(Context context, List<Genre> genres, GenresCallbacks callbacks) {
        this.mContext = context;
        this.mGeneros = genres;
        this.mCallback = callbacks;
    }

    public void setGeneros(List<Genre> genre) {
        this.mGeneros = genre;
    }

    @Override
    public GenresActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_genres, parent, false);

        return new GenresActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenresActivityViewHolder holder, int position) {
        holder.bindGenero(mGeneros.get(position));
    }

    @Override
    public int getItemCount() {
        return mGeneros.size();
    }

    class GenresActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.genre_background)    ImageView mGenresBackground;
        @BindView(R.id.title_genre)         TextView mTitleGenres;
        @BindView(R.id.card_view)           CardView mCardView;

        private Genre mGenero;

        public GenresActivityViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bindGenero(Genre gene) {
            this.mGenero = gene;

            ImageUtils.load(mContext, mGenero.getImgPath(), R.drawable.placeholder_images_default, mGenresBackground);
            mTitleGenres.setText(mGenero.getName().toUpperCase());
            mCardView.setLayoutParams(new CardView.LayoutParams(mGenresBackground.getLayoutParams().width, mGenresBackground.getLayoutParams().height));
        }

        @Override
        public void onClick(View view) {
            mCallback.onGenreSelected(mGenero);
        }
    }
}
