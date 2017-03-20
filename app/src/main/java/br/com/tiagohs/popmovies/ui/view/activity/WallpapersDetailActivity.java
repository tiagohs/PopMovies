package br.com.tiagohs.popmovies.ui.view.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.model.dto.ImageSizeDTO;
import br.com.tiagohs.popmovies.ui.adapters.WallpaperPagerAdapter;
import br.com.tiagohs.popmovies.ui.tools.ViewPagerWallpapers;
import br.com.tiagohs.popmovies.util.PermissionUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.ServerUtils;
import br.com.tiagohs.popmovies.util.ShareUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.util.enumerations.TypeShowImage;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class WallpapersDetailActivity extends AppCompatActivity {
    private static final String TAG = WallpapersDetailActivity.class.getSimpleName();

    private static final String ARG_IMAGES = "br.com.tiagohs.popmovies.images";
    private static final String ARG_IMAGE_CURRENT = "br.com.tiagohs.popmovies.imageCurrent";
    private static final String ARG_WALLPAPERS_PAGE_TITLE = "br.com.tiagohs.popmovies.page_Wallpapers_title";
    private static final String ARG_WALLPAPERS_PAGE_SUBTITLE = "br.com.tiagohs.popmovies.page_Wallpapers_subtitle";
    public static final String ARG_TYPE_SHOW_IMAGE = "br.com.tiagohs.popmovies.type_show_image";

    @BindView(R.id.toolbar)                 Toolbar mToolbar;
    @BindView(R.id.coordenation_layout)     CoordinatorLayout coordinatorLayout;
    @BindView(R.id.wallpaper_view_pager)    ViewPagerWallpapers mWallpaperViewPager;
    @BindView(R.id.progress)                ProgressWheel mProgress;

    private Unbinder mBinder;

    private List<ImageDTO> mImagens;
    private ImageDTO mImageCurrent;
    private String mPageTitle;
    private String mPageSubtitle;
    private WallpaperPagerAdapter mWallpaperPagerAdapter;
    private TypeShowImage mTypeShowImage;

    public static Intent newIntent(Context context, List<ImageDTO> mImagens, ImageDTO positioImage, String pageTitle, String pageSubtitle, TypeShowImage typeShowImage) {
        Intent intent = new Intent(context, WallpapersDetailActivity.class);
        intent.putParcelableArrayListExtra(ARG_IMAGES, (ArrayList<ImageDTO>) mImagens);
        intent.putExtra(ARG_IMAGE_CURRENT, positioImage);
        intent.putExtra(ARG_WALLPAPERS_PAGE_TITLE, pageTitle);
        intent.putExtra(ARG_WALLPAPERS_PAGE_SUBTITLE, pageSubtitle);
        intent.putExtra(ARG_TYPE_SHOW_IMAGE, typeShowImage);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpapers_detail);

        mBinder = ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mImagens = getIntent().getParcelableArrayListExtra(ARG_IMAGES);
        mImageCurrent = getIntent().getParcelableExtra(ARG_IMAGE_CURRENT);
        mPageTitle = getIntent().getStringExtra(ARG_WALLPAPERS_PAGE_TITLE);
        mPageSubtitle = getIntent().getStringExtra(ARG_WALLPAPERS_PAGE_SUBTITLE);
        mTypeShowImage = (TypeShowImage) getIntent().getSerializableExtra(ARG_TYPE_SHOW_IMAGE);

        mWallpaperPagerAdapter = new WallpaperPagerAdapter(this, mImagens, mTypeShowImage);
        mWallpaperViewPager.setAdapter(mWallpaperPagerAdapter);
        setCurrentImage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinder.unbind();
    }

    public void setProgress(ProgressWheel progress) {
        mProgress = progress;
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
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }

    }

    public void onSaveImageClicked() {

        if (!ServerUtils.isWifiConnected(this)) {
            new MaterialDialog.Builder(this)
                    .title(getString(R.string.permission_error_title))
                    .content(getString(R.string.wifi_dialog_content))
                    .positiveText(getString(R.string.btn_yes))
                    .negativeText(getString(R.string.btn_no))
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            onCheckPermissionsToSave();
                        }
                    })
                    .show();
        } else {
            onCheckPermissionsToSave();
        }

    }

    private void onCheckPermissionsToSave() {

        if (PermissionUtils.validatePermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, getString(R.string.error_permission_save)))
            salvarImagem();
        else
            mProgress.setVisibility(View.GONE);
    }

    private void salvarImagem() {

        final List<ImageSizeDTO> imagesSizes = new ArrayList<>();

        imagesSizes.add(new ImageSizeDTO("** x 300 pixels", "w300"));
        imagesSizes.add(new ImageSizeDTO("** x 780 pixels", "w780"));
        imagesSizes.add(new ImageSizeDTO("** x 1280 pixels", "w1280"));
        imagesSizes.add(new ImageSizeDTO("Tamanho original", "original"));

        new MaterialDialog.Builder(this)
                          .title(getString(R.string.wallpapers_dialog_size))
                          .items(imagesSizes)
                          .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                              @Override
                              public boolean onSelection(MaterialDialog dialog, View itemView, int which, CharSequence text) {
                                  onQualitySelected(imagesSizes.get(which).getSize());

                                  return true;
                              }
                          })
                          .positiveText(getString(R.string.btn_ok))
                          .onAny(new MaterialDialog.SingleButtonCallback() {
                              @Override
                              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                  mProgress.setVisibility(View.GONE);
                              }
                          })
                        .show();

    }

    private void onQualitySelected(String size) {
        mProgress.setVisibility(View.VISIBLE);
        Toast.makeText(WallpapersDetailActivity.this, getString(R.string.sucess_image_saved), Toast.LENGTH_LONG).show();

        DownloadManager mgr = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        String path = "";
        String nameImage = "";

        if (mTypeShowImage.equals(TypeShowImage.SIMPLE_IMAGE)) {
            nameImage = getString(R.string.downloaded_image_perfil_name, PrefsUtils.getCurrentProfile(this).getProfileID());
            path = mImagens.get(mWallpaperViewPager.getCurrentItem()).getImagePath();
        } else if (mTypeShowImage.equals(TypeShowImage.WALLPAPER_IMAGES)) {
            nameImage = getString(R.string.downloaded_image_perfil_name, mImagens.get(mWallpaperViewPager.getCurrentItem()).getMovieID());
            path = getString(R.string.url_image, size, mImagens.get(mWallpaperViewPager.getCurrentItem()).getImagePath());
        }

        Uri downloadUri = Uri.parse(path);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedOverRoaming(false).setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.downloaded_baixando_content))
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationInExternalPublicDir(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath() + getString(R.string.downloaded_name_folder), nameImage);

        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                Toast.makeText(WallpapersDetailActivity.this, getString(R.string.sucess_image_saved), Toast.LENGTH_SHORT).show();
                mProgress.setVisibility(View.GONE);
            }
        };

        registerReceiver(onComplete, new
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        mgr.enqueue(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PermissionUtils.onRequestPermissionsResultValidate(grantResults, requestCode))
            createShareIntent();
        else
            mProgress.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (mTypeShowImage.equals(TypeShowImage.SIMPLE_IMAGE)) {
            getMenuInflater().inflate(R.menu.menu_wallpapers_simple, menu);
        } else if (mTypeShowImage.equals(TypeShowImage.WALLPAPER_IMAGES)) {
            getMenuInflater().inflate(R.menu.menu_wallpapers, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.menu_go_gallery:
                startActivity(WallpapersActivity.newIntent(this, mImagens, mPageTitle, mPageSubtitle));
                return true;
            case R.id.menu_save:
                onSaveImageClicked();
                return true;
            case R.id.menu_share:
                share();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void share() {

        if (PermissionUtils.validatePermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, getString(R.string.permission_error_write_content)))
            createShareIntent();
        else
            mProgress.setVisibility(View.GONE);
    }

    public void createShareIntent() {

        ShareUtils.shareImage(this, getString(R.string.url_image, ImageSize.BACKDROP_780.getSize(),
                mImagens.get(mWallpaperViewPager.getCurrentItem()).getImagePath()),
                getString(R.string.downloaded_image_name, mImagens.get(mWallpaperViewPager.getCurrentItem()).getMovieID()),
                mProgress);

    }

}
