package br.com.tiagohs.popmovies.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCast;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.view.fragment.MovieDetailsTecnicoFragment;

/**
 * Created by Tiago Henrique on 27/08/2016.
 */
public class ElencoAdapter extends RecyclerView.Adapter<ElencoAdapter.ElencoViewHolder> {
    private Context mContext;
    private List<MediaCreditCast> mElenco;
    private MovieDetailsTecnicoFragment.Callbacks mCallbacks;

    public ElencoAdapter(Context context, List<MediaCreditCast> cast, MovieDetailsTecnicoFragment.Callbacks callbacks) {
        this.mContext = context;
        this.mElenco = cast;
        this.mCallbacks = callbacks;
    }

    public void setElenco(List<MediaCreditCast> genre) {
        this.mElenco = genre;
    }

    @Override
    public ElencoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_person_default, parent, false);

        return new ElencoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ElencoViewHolder holder, int position) {
        holder.bindElenco(mElenco.get(position));
    }

    @Override
    public int getItemCount() {
        return mElenco.size();
    }

    class ElencoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MediaCreditCast mCast;
        private TextView mNomeTextView;
        private TextView mSubtituloTextView;
        private CircularImageView mImagemPerson;
        private ProgressWheel mProgress;

        public ElencoViewHolder(View itemView) {
            super(itemView);

            mImagemPerson = (CircularImageView) itemView.findViewById(R.id.imagem_credit_movie);
            mNomeTextView = (TextView) itemView.findViewById(R.id.nome_profissional_movie);
            mSubtituloTextView = (TextView) itemView.findViewById(R.id.subtitulo_item_person_movie);
            mProgress = (ProgressWheel) itemView.findViewById(R.id.person_movies_progress);

            itemView.setOnClickListener(this);
        }

        public void bindElenco(MediaCreditCast cast) {
            Log.i("AtorAdp: ", "Ator!" + cast.getName());
            this.mCast = cast;

            ImageUtils.loadByCircularImage(mContext, mCast.getArtworkPath(), mImagemPerson, R.drawable.background_oval, R.mipmap.ic_person, ImageSize.POSTER_154, mProgress);
            mNomeTextView.setText(mCast.getName());
            mSubtituloTextView.setText(mCast.getCharacter());
        }

        @Override
        public void onClick(View view) {
            mCallbacks.onClickCast(mCast.getId());
        }
    }
}
