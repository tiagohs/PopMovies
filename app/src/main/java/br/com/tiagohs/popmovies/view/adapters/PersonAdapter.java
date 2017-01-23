package br.com.tiagohs.popmovies.view.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
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
import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ElencoViewHolder> {
    private Context mContext;
    private List<PersonListDTO> mElenco;
    private PersonCallbacks mCallbacks;
    private Fragment mFragment;

    public PersonAdapter(Context context, Fragment fragment, List<PersonListDTO> cast, PersonCallbacks callbacks) {
        this.mContext = context;
        this.mElenco = cast;
        this.mCallbacks = callbacks;
        this.mFragment = fragment;
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

        @BindView(R.id.nome_profissional_movie)
        TextView mNomeTextView;

        @BindView(R.id.subtitulo_item_person_movie)
        TextView mSubtituloTextView;

        @BindView(R.id.image_circle)
        ImageView mImagemPerson;

        private PersonListDTO mPerson;

        public ElencoViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bindElenco(PersonListDTO person) {
            this.mPerson = person;

            if (mFragment.isAdded()) {
                ImageUtils.loadByCircularImage(mContext, mPerson.getProfilePath(), mImagemPerson, mPerson.getNamePerson(), ImageSize.PROFILE_185);
                mNomeTextView.setText(mPerson.getNamePerson());
                mSubtituloTextView.setText(mPerson.getSubtitulo());
            }

        }

        @Override
        public void onClick(View view) {
            mCallbacks.onClickPerson(mPerson.getID());
        }
    }
}
