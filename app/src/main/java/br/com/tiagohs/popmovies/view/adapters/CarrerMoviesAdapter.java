package br.com.tiagohs.popmovies.view.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.CarrerMoviesDTO;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.enumerations.CreditType;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.view.callbacks.ListMoviesCallbacks;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tiago Henrique on 04/09/2016.
 */
public class CarrerMoviesAdapter extends RecyclerView.Adapter<CarrerMoviesAdapter.ListMoviesViewHolder> {
    private List<CarrerMoviesDTO> list;
    private Context mContext;
    private ListMoviesCallbacks mCallbacks;
    private int mLayoutMovieResID;
    private Fragment mFragment;

    public CarrerMoviesAdapter(Context context, Fragment fragment, List<CarrerMoviesDTO> list, ListMoviesCallbacks callbacks, int layoutMovieResID) {
        this.list = list;
        this.mContext = context;
        this.mCallbacks = callbacks;
        this.mLayoutMovieResID = layoutMovieResID;
        this.mFragment = fragment;
    }

    public void setList(List<CarrerMoviesDTO> list) {
        this.list = list;
        Log.i("Carrer", this.list.size() + "");
    }

    public void setAllList(List<CarrerMoviesDTO> list) {
        list.addAll(list);
    }

    @Override
    public ListMoviesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(mLayoutMovieResID, parent, false);

        return new ListMoviesViewHolder(mContext, view);
    }

    @Override
    public void onBindViewHolder(ListMoviesViewHolder holder, int position) {
        holder.bindMovie(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ListMoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.poster_movie)                            ImageView mPosterMovie;
        @BindView(R.id.person_carrer_movie_title)               TextView mTitle;
        @BindView(R.id.person_carrer_movie_title_original)      TextView mTitleOriginal;
        @BindView(R.id.person_carrer_movie_year)                TextView mYear;
        @BindView(R.id.person_carrer_movie_character)           TextView mCharacter;

        private CarrerMoviesDTO mMovie;

        public ListMoviesViewHolder(final Context context, View itemView) {
            super(itemView);
            mContext = context;

            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void bindMovie(CarrerMoviesDTO movie) {
            mMovie = movie;

            if (mFragment.isAdded()) {
                Typeface openSans = Typeface.createFromAsset(mContext.getAssets(), "opensans.ttf");

                mTitle.setText(movie.getTitle());
                mTitle.setTypeface(openSans);

                mTitleOriginal.setText(movie.getOriginalTitle());
                mTitle.setTypeface(openSans);

                mYear.setText(String.valueOf(movie.getYearRelease()));
                mYear.setTypeface(openSans);

                mCharacter.setText(mMovie.getCreditType().equals(CreditType.CAST) ?
                        mContext.getResources().getString(R.string.person_carrer_movie_character, movie.getCharacter()) :
                        mContext.getResources().getString(R.string.person_carrer_movie_departamento, movie.getDepartment()));

                mCharacter.setTypeface(openSans);
                ImageUtils.loadByCircularImage(mContext, movie.getPosterPath(), mPosterMovie, mMovie.getTitle(), ImageSize.POSTER_92);
            }
         }


        @Override
        public void onClick(View view) {
            mCallbacks.onMovieSelected(mMovie.getId(), null);
        }
    }
}
