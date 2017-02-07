package br.com.tiagohs.popmovies.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.util.enumerations.TypeShowImage;
import br.com.tiagohs.popmovies.view.adapters.ImageAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ImagesCallbacks;
import butterknife.BindView;

public class WallpapersActivity extends BaseActivity implements ImagesCallbacks {
    public static final String ARG_WALLPAPERS = "br.com.tiagohs.popmovies.wallpapers";
    public static final String ARG_TITLE_PAGE = "br.com.tiagohs.popmovies.title_page_wall";
    public static final String ARG_SUBTITLE_PAGE = "br.com.tiagohs.popmovies.subtitle_page_wall";

    @BindView(R.id.wallpapers_recycler_view)        RecyclerView mWallpapersRecyclerView;
    @BindView(R.id.wallpapers_principal_progress)   ProgressWheel mProgress;
    @BindView(R.id.wallpaper_nao_encontrado)        TextView mWallpapersNaoEncontrados;

    private List<ImageDTO> mWallpapers;
    private String mPageTitle;
    private String mPageSubtitle;

    public static Intent newIntent(Context context, List<ImageDTO> wallpapers, String pageTitle, String pageSubtitle) {
        Intent intent = new Intent(context, WallpapersActivity.class);
        intent.putExtra(ARG_WALLPAPERS, (ArrayList<ImageDTO>) wallpapers);
        intent.putExtra(ARG_TITLE_PAGE, pageTitle);
        intent.putExtra(ARG_SUBTITLE_PAGE, pageSubtitle);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWallpapers = (ArrayList<ImageDTO>) getIntent().getSerializableExtra(ARG_WALLPAPERS);
        mPageTitle = getIntent().getStringExtra(ARG_TITLE_PAGE);
        mPageSubtitle = getIntent().getStringExtra(ARG_SUBTITLE_PAGE);

        mToolbar.setTitle(mPageTitle);

        if (mPageSubtitle != null)
            mToolbar.setSubtitle(mPageSubtitle);

        configurateWallpapersRecyclerView();
    }

    private void configurateWallpapersRecyclerView() {

        if (mWallpapers == null)
            noImages();
        else if (mWallpapers.isEmpty())
            noImages();
        else {
            mWallpapersRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.images_movie_detail_columns)));
            mWallpapersRecyclerView.setAdapter(new ImageAdapter(this, mWallpapers, this, mWallpapers));
            mProgress.setVisibility(View.GONE);
        }

    }

    private void noImages() {
        mWallpapersNaoEncontrados.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.GONE);
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

    @Override
    protected int getMenuLayoutID() {
        return 0;
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_wallpapers;
    }

    @Override
    public void onClickImage(List<ImageDTO> imagens, ImageDTO imageDTO) {
        startActivity(WallpapersDetailActivity.newIntent(this, imagens, imageDTO, mPageTitle, mPageSubtitle, TypeShowImage.WALLPAPER_IMAGES));
    }
}
