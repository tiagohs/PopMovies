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

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.credits.MediaCreditCrew;

public class DiretoresAdapter extends RecyclerView.Adapter<DiretoresAdapter.DiretoresViewHolder> {
    private final String DIRECTOR = "Directing";

    private Context mContext;
    private List<MediaCreditCrew> mDirectors;

    public DiretoresAdapter(Context context, List<MediaCreditCrew> crews) {
        this.mContext = context;
        setDirectors(crews);
    }

    public void setDirectors(List<MediaCreditCrew> directors) {
        mDirectors = findDirector(directors);
    }

    private List<MediaCreditCrew> findDirector(List<MediaCreditCrew> crews) {
        List<MediaCreditCrew> directors = new ArrayList<>();

        for (MediaCreditCrew crew : crews) {
            if (crew.getDepartment().equals(DIRECTOR))
                directors.add(crew);

        }

        return directors;
    }

    @Override
    public DiretoresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.movies_item_default, parent, false);

        return new DiretoresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DiretoresViewHolder holder, int position) {
        holder.bindDirector(mDirectors.get(position));
    }

    @Override
    public int getItemCount() {
        return mDirectors.size();
    }

    class DiretoresViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MaterialRippleLayout mRippleView;
        private TextView mDirectorNameTextView;
        private MediaCreditCrew mDirector;

        public DiretoresViewHolder(View itemView) {
            super(itemView);
        }

        public void bindDirector(MediaCreditCrew director) {
            Log.i("DiretorAdp: ", "Diretor!" + director.getName());
            this.mDirector = director;

            mRippleView = (MaterialRippleLayout) itemView.findViewById(R.id.movie_deails_item_default_riple);
            mRippleView.setOnClickListener(this);
            mDirectorNameTextView = (TextView) itemView.findViewById(R.id.movie_deails_item_default_text_view);

            mDirectorNameTextView.setText(director.getName());
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(mContext, "Diretor: " + mDirector.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
