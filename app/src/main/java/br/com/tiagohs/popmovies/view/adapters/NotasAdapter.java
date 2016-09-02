package br.com.tiagohs.popmovies.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.Movie.MovieDetails;

/**
 * Created by Tiago Henrique on 27/08/2016.
 */
public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.NotasViewHolder> {
    private List<MovieDetails> mMovieDetails;
    private Context mContext;

    public NotasAdapter(Context context, List<MovieDetails> movieDetails) {
        this.mMovieDetails = movieDetails;
        this.mContext = context;
    }
    @Override
    public NotasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_notas__movie, parent, false);
        return new NotasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotasViewHolder holder, int position) {
        Log.i("NotaAd", "Notas: ");
        //holder.bind();
    }

    @Override
    public int getItemCount() {
        return mMovieDetails.size();
    }

    public void setMovieDetails(List<MovieDetails> movieDetails) {
        mMovieDetails = movieDetails;
    }

    class NotasViewHolder extends RecyclerView.ViewHolder {
        private TextView mNomeSite;
        private TextView mTotalVotos;
        private TextView mNota;
        private TextView mNotaTotal;

        public NotasViewHolder(View itemView) {
            super(itemView);

            mNomeSite = (TextView) itemView.findViewById(R.id.notas_nome_site);
            mTotalVotos = (TextView) itemView.findViewById(R.id.notas_total_votos);
            mNota = (TextView) itemView.findViewById(R.id.notas_nota);
            mNotaTotal = (TextView) itemView.findViewById(R.id.notas_nota_total);
        }
    }
}
