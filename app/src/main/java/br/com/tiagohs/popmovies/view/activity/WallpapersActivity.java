package br.com.tiagohs.popmovies.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.view.adapters.ImageAdapter;
import br.com.tiagohs.popmovies.view.callbacks.ImagesCallbacks;
import butterknife.BindView;

public class WallpapersActivity extends BaseActivity implements ImagesCallbacks {
    public static final String ARG_WALLPAPERS = "br.com.tiagohs.popmovies.wallpapers";
    public static final String ARG_TITLE_PAGE = "br.com.tiagohs.popmovies.title_page_wall";

    @BindView(R.id.wallpapers_recycler_view)        RecyclerView mWallpapersRecyclerView;

    private List<ImageDTO> mWallpapers;
    private String mPageTitle;

    public static Intent newIntent(Context context, List<ImageDTO> wallpapers, String pageTitle) {
        Intent intent = new Intent(context, WallpapersActivity.class);
        intent.putExtra(ARG_WALLPAPERS, (ArrayList<ImageDTO>) wallpapers);
        intent.putExtra(ARG_TITLE_PAGE, pageTitle);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWallpapers = (ArrayList<ImageDTO>) getIntent().getSerializableExtra(ARG_WALLPAPERS);
        mPageTitle = getIntent().getStringExtra(ARG_TITLE_PAGE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mToolbar.setTitle(mPageTitle);
        configurateWallpapersRecyclerView();
    }

    private void configurateWallpapersRecyclerView() {
        int columnCount = getResources().getInteger(R.integer.images_movie_detail_columns);
        //mWallpapersRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        mWallpapersRecyclerView.setLayoutManager(new GridLayoutManager(this, columnCount));
        mWallpapersRecyclerView.setAdapter(new ImageAdapter(this, mWallpapers, this, mWallpapers));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_wallpapers;
    }

    @Override
    public void onClickImage(List<ImageDTO> imagens, ImageDTO imageDTO) {
        startActivity(WallpapersDetailActivity.newIntent(this, imagens, imageDTO, mPageTitle));
    }
}
