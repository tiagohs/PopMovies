package br.com.tiagohs.popmovies.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ItemListDTO;
import br.com.tiagohs.popmovies.util.enumerations.ItemType;
import br.com.tiagohs.popmovies.ui.callbacks.ListWordsCallbacks;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tiago Henrique on 27/08/2016.
 */
public class ListWordsAdapter extends RecyclerView.Adapter<ListWordsAdapter.GenerosViewHolder> {

    private Context mContext;
    private List<ItemListDTO> mItemListDTOs;
    private ListWordsCallbacks mCallbacks;
    private ItemType mItemType;
    private int mLayoutID;

    public ListWordsAdapter(Context context, List<ItemListDTO> genres, ListWordsCallbacks callbacks, ItemType itemType, int layoutID) {
        this.mContext = context;
        this.mItemListDTOs = genres;
        this.mCallbacks = callbacks;
        this.mItemType = itemType;
        this.mLayoutID = layoutID;
    }

    public void setItemListDTOs(List<ItemListDTO> itemListDTOs) {
        mItemListDTOs = itemListDTOs;
    }

    @Override
    public GenerosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(mLayoutID, parent, false);

        return new GenerosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenerosViewHolder holder, int position) {
        holder.bindGenero(mItemListDTOs.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemListDTOs.size();
    }

    class GenerosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.movie_deails_item_default_riple)         MaterialRippleLayout mRippleView;
        @BindView(R.id.movie_deails_item_default_text_view)     TextView mGeneroNameTextView;

        private ItemListDTO mItemListDTO;

        public GenerosViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            mRippleView.setOnClickListener(this);
        }

        public void bindGenero(ItemListDTO item) {
            this.mItemListDTO = item;

            mGeneroNameTextView.setText(mItemListDTO.getNameItem());
        }

        @Override
        public void onClick(View view) {
            mCallbacks.onItemSelected(mItemListDTO, mItemType);
        }
    }
}
