package br.com.tiagohs.popmovies.view.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.movie.Genre;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.view.fragment.GenresCallbacks;

public class GenresActivityAdapter extends RecyclerView.Adapter<GenresActivityAdapter.GenresActivityViewHolder> {

    private Context mContext;
    private GenresCallbacks mCallback;
    private List<Genre> mGeneros;

    public GenresActivityAdapter(Context context, List<Genre> genres, GenresCallbacks callbacks) {
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
        private Genre mGenero;
        private ImageView mGenresBackground;
        private TextView mTitleGenres;
        private CardView mCardView;
        private MaterialRippleLayout mMaterialRippleLayout;

        public GenresActivityViewHolder(View itemView) {
            super(itemView);

            mGenresBackground = (ImageView) itemView.findViewById(R.id.genre_background);
            mCardView = (CardView) itemView.findViewById(R.id.card_view);
            mMaterialRippleLayout = (MaterialRippleLayout) itemView.findViewById(R.id.movie_deails_item_default_riple);

            mTitleGenres = (TextView) itemView.findViewById(R.id.title_genre);
            mTitleGenres.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "opensans.ttf"));

            itemView.setOnClickListener(this);
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
