package br.com.tiagohs.popmovies.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.ui.adapters.ImageAdapter;
import br.com.tiagohs.popmovies.ui.callbacks.ImagesCallbacks;
import butterknife.BindView;

public class WallpapersFragment extends BaseFragment {
    public static final String ARG_WALLPAPERS = "br.com.tiagohs.popmovies.wallpapers";

    @BindView(R.id.wallpapers_recycler_view)            RecyclerView mWallpapersRecyclerView;
    @BindView(R.id.wallpapers_principal_progress)       ProgressWheel mProgress;
    @BindView(R.id.wallpaper_nao_encontrado)            TextView mWallpapersNaoEncontrados;

    private List<ImageDTO> mWallpapers;

    public static WallpapersFragment newInstance(List<ImageDTO> wallpapers) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ARG_WALLPAPERS, (ArrayList<ImageDTO>) wallpapers);

        WallpapersFragment wallpapersFragment = new WallpapersFragment();
        wallpapersFragment.setArguments(bundle);

        return wallpapersFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWallpapers = getArguments().getParcelableArrayList(ARG_WALLPAPERS);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configurateWallpapersRecyclerView();
    }

    private void configurateWallpapersRecyclerView() {

        if (mWallpapers == null)
            noImages();
        else if (mWallpapers.isEmpty())
            noImages();
        else {
            mWallpapersRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.images_movie_detail_columns)));
            mWallpapersRecyclerView.setAdapter(new ImageAdapter(getContext(), mWallpapers, (ImagesCallbacks) getActivity(), mWallpapers, isTablet()));
            mProgress.setVisibility(View.GONE);
        }

    }

    private void noImages() {
        mWallpapersNaoEncontrados.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_wallpapers;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configurateWallpapersRecyclerView();
                mSnackbar.dismiss();
            }
        };
    }

}
