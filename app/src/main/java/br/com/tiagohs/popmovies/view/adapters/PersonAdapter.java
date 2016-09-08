package br.com.tiagohs.popmovies.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.PersonListDTO;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.view.callbacks.PersonCallbacks;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ElencoViewHolder> {
    private Context mContext;
    private List<PersonListDTO> mElenco;
    private PersonCallbacks mCallbacks;

    public PersonAdapter(Context context, List<PersonListDTO> cast, PersonCallbacks callbacks) {
        this.mContext = context;
        this.mElenco = cast;
        this.mCallbacks = callbacks;
    }

    public void setElenco(List<PersonListDTO> genre) {
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
        private PersonListDTO mPerson;
        private TextView mNomeTextView;
        private TextView mSubtituloTextView;
        private ImageView mImagemPerson;

        public ElencoViewHolder(View itemView) {
            super(itemView);

            mImagemPerson = (ImageView) itemView.findViewById(R.id.imagem_credit_movie);
            mNomeTextView = (TextView) itemView.findViewById(R.id.nome_profissional_movie);
            mSubtituloTextView = (TextView) itemView.findViewById(R.id.subtitulo_item_person_movie);

            itemView.setOnClickListener(this);
        }

        public void bindElenco(PersonListDTO person) {
            this.mPerson = person;

            ImageUtils.loadByCircularImage(mContext, mPerson.getProfilePath(), mImagemPerson, mPerson.getNamePerson(), ImageSize.PROFILE_185);
            mNomeTextView.setText(mPerson.getNamePerson());
            mSubtituloTextView.setText(mPerson.getSubtitulo());
        }

        @Override
        public void onClick(View view) {
            mCallbacks.onClickPerson(mPerson.getID());
        }
    }
}
