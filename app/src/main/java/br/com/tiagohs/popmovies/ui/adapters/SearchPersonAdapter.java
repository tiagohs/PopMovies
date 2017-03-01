package br.com.tiagohs.popmovies.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.person.PersonFind;
import br.com.tiagohs.popmovies.ui.callbacks.PersonCallbacks;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tiago on 14/01/2017.
 */

public class SearchPersonAdapter extends RecyclerView.Adapter<SearchPersonAdapter.SearchViewHolder> {
    private List<PersonFind> list;
    private Context mContext;
    private PersonCallbacks mPersonCallbacks;

    public SearchPersonAdapter(Context context, List<PersonFind> list, PersonCallbacks callbacks) {
        this.list = list;
        this.mContext = context;
        this.mPersonCallbacks = callbacks;
    }

    public void setList(List<PersonFind> list) {
        this.list = list;
    }

    @Override
    public SearchPersonAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_search_person, parent, false);

        return new SearchPersonAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchPersonAdapter.SearchViewHolder holder, int position) {
        holder.bindPerson(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image_circle)    ImageView mImageView;
        @BindView(R.id.person_name)     TextView mName;

        private PersonFind mPerson;

        public SearchViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bindPerson(PersonFind person) {
            mPerson = person;

            ImageUtils.loadByCircularImage(mContext, person.getProfilePath(), mImageView, person.getName(), ImageSize.PROFILE_185);
            mName.setText(person.getName());
        }


        @Override
        public void onClick(View view) {
            mPersonCallbacks.onClickPerson(mPerson.getId());
        }

    }
}
