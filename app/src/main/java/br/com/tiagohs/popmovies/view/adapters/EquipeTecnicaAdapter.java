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
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCrew;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.util.ImageUtils;

public class EquipeTecnicaAdapter extends RecyclerView.Adapter<EquipeTecnicaAdapter.EquipeTecnicaViewHolder> {
    private Context mContext;
    private List<MediaCreditCrew> mEquipeTecnica;

    public EquipeTecnicaAdapter(Context context, List<MediaCreditCrew> cast) {
        this.mContext = context;
        this.mEquipeTecnica = cast;
    }

    public void setEquipeTecnica(List<MediaCreditCrew> genre) {
        this.mEquipeTecnica = genre;
    }

    @Override
    public EquipeTecnicaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_person_default, parent, false);

        return new EquipeTecnicaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EquipeTecnicaViewHolder holder, int position) {
        holder.bindEquipeTecnica(mEquipeTecnica.get(position));
    }

    @Override
    public int getItemCount() {
        return mEquipeTecnica.size();
    }

    class EquipeTecnicaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MediaCreditCrew mCrew;
        private TextView mNomeTextView;
        private TextView mSubtituloTextView;
        private CircularImageView mImagemPerson;
        private ProgressWheel mProgress;

        public EquipeTecnicaViewHolder(View itemView) {
            super(itemView);

            mImagemPerson = (CircularImageView) itemView.findViewById(R.id.imagem_credit_movie);
            mNomeTextView = (TextView) itemView.findViewById(R.id.nome_profissional_movie);
            mSubtituloTextView = (TextView) itemView.findViewById(R.id.subtitulo_item_person_movie);
            mProgress = (ProgressWheel) itemView.findViewById(R.id.person_movies_progress);
        }

        public void bindEquipeTecnica(MediaCreditCrew crew) {
            Log.i("EquipeAdp: ", "Equipe!" + crew.getName());
            this.mCrew = crew;

            ImageUtils.loadByCircularImage(mContext, mCrew.getArtworkPath(), mImagemPerson, R.drawable.background_oval, R.mipmap.ic_person, ImageSize.POSTER_154, mProgress);
            mNomeTextView.setText(mCrew.getName());
            mSubtituloTextView.setText(mCrew.getDepartment());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, "Ator: " + mCrew.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
