package br.com.tiagohs.popmovies.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.keyword.Keyword;

/**
 * Created by Tiago Henrique on 27/08/2016.
 */
public class KeywordsAdapter  extends RecyclerView.Adapter<KeywordsAdapter.KeywordViewHolder> {

    private Context mContext;
    private List<Keyword> mKeywords;

    public KeywordsAdapter(Context context, List<Keyword> genres) {
        this.mContext = context;
        this.mKeywords = genres;
    }

    public void setKeywords(List<Keyword> genre) {
        this.mKeywords = genre;
    }

    @Override
    public KeywordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_list_words_default, parent, false);

        return new KeywordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(KeywordViewHolder holder, int position) {
        holder.bindGenero(mKeywords.get(position));
    }

    @Override
    public int getItemCount() {
        return mKeywords.size();
    }

    class KeywordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MaterialRippleLayout mRippleView;
        private TextView mKeywordNameTextView;
        private Keyword mKeyword;

        public KeywordViewHolder(View itemView) {
            super(itemView);

            mRippleView = (MaterialRippleLayout) itemView.findViewById(R.id.movie_deails_item_default_riple);
            mRippleView.setOnClickListener(this);
            mKeywordNameTextView = (TextView) itemView.findViewById(R.id.movie_deails_item_default_text_view);
        }

        public void bindGenero(Keyword keyword) {
            Log.i("KeywordAdp: ", "Keyword!" + keyword.getName());
            this.mKeyword = keyword;

            mKeywordNameTextView.setText(mKeyword.getName());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, "Keyword: " + mKeyword.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
