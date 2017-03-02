package br.com.tiagohs.popmovies.ui.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.ui.view.fragment.WallpapersFragment;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import br.com.tiagohs.popmovies.util.enumerations.TypeShowImage;
import br.com.tiagohs.popmovies.ui.callbacks.ImagesCallbacks;

public class WallpapersActivity extends BaseActivity implements ImagesCallbacks {
    public static final String ARG_WALLPAPERS = "br.com.tiagohs.popmovies.wallpapers";
    public static final String ARG_TITLE_PAGE = "br.com.tiagohs.popmovies.title_page_wall";
    public static final String ARG_SUBTITLE_PAGE = "br.com.tiagohs.popmovies.subtitle_page_wall";

    private List<ImageDTO> mWallpapers;
    private String mPageTitle;
    private String mPageSubtitle;

    public static Intent newIntent(Context context, List<ImageDTO> wallpapers, String pageTitle, String pageSubtitle) {
        Intent intent = new Intent(context, WallpapersActivity.class);
        intent.putParcelableArrayListExtra(ARG_WALLPAPERS, (ArrayList<ImageDTO>) wallpapers);
        intent.putExtra(ARG_TITLE_PAGE, pageTitle);
        intent.putExtra(ARG_SUBTITLE_PAGE, pageSubtitle);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWallpapers = getIntent().getParcelableArrayListExtra(ARG_WALLPAPERS);
        mPageTitle = getIntent().getStringExtra(ARG_TITLE_PAGE);
        mPageSubtitle = getIntent().getStringExtra(ARG_SUBTITLE_PAGE);

        mToolbar.setTitle(mPageTitle);

        if (EmptyUtils.isNotNull(mPageSubtitle))
            mToolbar.setSubtitle(mPageSubtitle);

        startFragment(R.id.content_fragment, WallpapersFragment.newInstance(mWallpapers));
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    protected int getMenuLayoutID() {
        return 0;
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_default;
    }

    @Override
    public void onClickImage(List<ImageDTO> imagens, ImageDTO imageDTO) {
        startActivity(WallpapersDetailActivity.newIntent(this, imagens, imageDTO, mPageTitle, mPageSubtitle, TypeShowImage.WALLPAPER_IMAGES));
    }
}
