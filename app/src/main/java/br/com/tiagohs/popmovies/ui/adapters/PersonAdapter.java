package br.com.tiagohs.popmovies.ui.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
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
import br.com.tiagohs.popmovies.ui.callbacks.PersonCallbacks;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ElencoViewHolder> {
    private Context mContext;
    private List<PersonListDTO> mElenco;
    private PersonCallbacks mCallbacks;
    private Fragment mFragment;
    private boolean mIsTablet;
    private int mLayoutID;

    public PersonAdapter(Context context, Fragment fragment, List<PersonListDTO> cast, PersonCallbacks callbacks, boolean isTablet, int layoutID) {
        this.mContext = context;
        this.mElenco = cast;
        this.mCallbacks = callbacks;
        this.mFragment = fragment;
        this.mIsTablet = isTablet;
        this.mLayoutID = layoutID;
    }

    public void setElenco(List<PersonListDTO> genre) {
        this.mElenco = genre;
    }

    @Override
    public ElencoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(mLayoutID, parent, false);

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
        @BindView(R.id.nome_profissional_movie)                   TextView mNomeTextView;
        @Nullable @BindView(R.id.subtitulo_item_person_movie)     TextView mSubtituloTextView;
        @BindView(R.id.image_circle)                              CircleImageView mImagemPerson;

        private PersonListDTO mPerson;

        public ElencoViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bindElenco(PersonListDTO person) {
            this.mPerson = person;

            if (mFragment.isAdded()) {
                ImageUtils.loadByCircularImage(mContext, mPerson.getProfilePath(), mImagemPerson, mPerson.getNamePerson(), mIsTablet ? ImageSize.PROFILE_ORIGINAL : ImageSize.PROFILE_185);

                if (mIsTablet)
                    mImagemPerson.setScaleType(ImageView.ScaleType.CENTER_CROP);

                mNomeTextView.setText(mPerson.getNamePerson());

                if (EmptyUtils.isNotNull(mSubtituloTextView))
                    mSubtituloTextView.setText(mPerson.getSubtitulo());
            }

        }

        @Override
        public void onClick(View view) {
            mCallbacks.onClickPerson(mPerson.getID());
        }
    }
}
