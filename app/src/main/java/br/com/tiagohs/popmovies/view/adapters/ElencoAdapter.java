package br.com.tiagohs.popmovies.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCast;
import br.com.tiagohs.popmovies.util.ImageSize;
import br.com.tiagohs.popmovies.util.ImageUtils;

/**
 * Created by Tiago Henrique on 27/08/2016.
 */
public class ElencoAdapter extends RecyclerView.Adapter<ElencoAdapter.ElencoViewHolder> {
    private Context mContext;
    private List<MediaCreditCast> mElenco;

    public ElencoAdapter(Context context, List<MediaCreditCast> cast) {
        this.mContext = context;
        this.mElenco = cast;
    }

    public void setElenco(List<MediaCreditCast> genre) {
        this.mElenco = genre;
    }

    @Override
    public ElencoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.person_item_movie, parent, false);

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

        public ElencoViewHolder(View itemView) {
            super(itemView);

            mImagemPerson = (CircularImageView) itemView.findViewById(R.id.imagem_credit_movie);
            mNomeTextView = (TextView) itemView.findViewById(R.id.nome_profissional_movie);
            mSubtituloTextView = (TextView) itemView.findViewById(R.id.subtitulo_item_person_movie);
        }

        public void bindElenco(MediaCreditCast cast) {
            Log.i("AtorAdp: ", "Ator!" + cast.getName());
            this.mCast = cast;

            ImageUtils.loadByCircularImage(mContext, mCast.getArtworkPath(), mImagemPerson, R.mipmap.ic_person, ImageSize.POSTER_154);
            mNomeTextView.setText(mCast.getName());
            mSubtituloTextView.setText(mCast.getCharacter());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, "Ator: " + mCast.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
