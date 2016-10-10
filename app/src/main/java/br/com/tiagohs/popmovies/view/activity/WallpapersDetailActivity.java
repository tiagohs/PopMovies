package br.com.tiagohs.popmovies.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.view.adapters.WallpaperPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WallpapersDetailActivity extends AppCompatActivity {

    private static final String ARG_IMAGES = "br.com.tiagohs.popmovies.images";
    private static final String ARG_IMAGE_CURRENT = "br.com.tiagohs.popmovies.imageCurrent";
    private static final String ARG_WALLPAPERS_PAGE_TITLE = "br.com.tiagohs.popmovies.page_Wallpapers_title";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.coordenation_layout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.wallpaper_view_pager)
    ViewPager mWallpaperViewPager;

    private List<ImageDTO> mImagens;
    private ImageDTO mImageCurrent;
    private String mPageTitle;

    public static Intent newIntent(Context context, List<ImageDTO> mImagens, ImageDTO positioImage, String pageTitle) {
        Intent intent = new Intent(context, WallpapersDetailActivity.class);
        intent.putExtra(ARG_IMAGES, (ArrayList<ImageDTO>) mImagens);
        intent.putExtra(ARG_IMAGE_CURRENT, positioImage);
        intent.putExtra(ARG_WALLPAPERS_PAGE_TITLE, pageTitle);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpapers_detail);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mImagens = (ArrayList<ImageDTO>) getIntent().getSerializableExtra(ARG_IMAGES);
        mImageCurrent = (ImageDTO) getIntent().getSerializableExtra(ARG_IMAGE_CURRENT);
        mPageTitle = getIntent().getStringExtra(ARG_WALLPAPERS_PAGE_TITLE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mWallpaperViewPager.setAdapter(new WallpaperPagerAdapter(this, mImagens, getSupportActionBar()));
        setCurrentImage();
    }

    private void setCurrentImage() {
        for (int i = 0; i < mImagens.size(); i++) {
            if (mImagens.get(i).equals(mImageCurrent))
                mWallpaperViewPager.setCurrentItem(i);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mWallpaperViewPager.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_wallpapers, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_go_gallery:
                startActivity(WallpapersActivity.newIntent(this, mImagens, mPageTitle));
            default:
                return false;
        }
    }

}
