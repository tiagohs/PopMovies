package br.com.tiagohs.popmovies.view.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.util.BasicImageDownloader;
import br.com.tiagohs.popmovies.util.BitmatCreator;
import br.com.tiagohs.popmovies.util.ImageSaver;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.view.adapters.WallpaperPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WallpapersDetailActivity extends AppCompatActivity {
    private static final String TAG = WallpapersDetailActivity.class.getSimpleName();

    private static final String ARG_IMAGES = "br.com.tiagohs.popmovies.images";
    private static final String ARG_IMAGE_CURRENT = "br.com.tiagohs.popmovies.imageCurrent";
    private static final String ARG_WALLPAPERS_PAGE_TITLE = "br.com.tiagohs.popmovies.page_Wallpapers_title";

    @BindView(R.id.toolbar)                 Toolbar mToolbar;
    @BindView(R.id.coordenation_layout)     CoordinatorLayout coordinatorLayout;
    @BindView(R.id.wallpaper_view_pager)    ViewPager mWallpaperViewPager;

    private List<ImageDTO> mImagens;
    private ImageDTO mImageCurrent;
    private String mPageTitle;
    private WallpaperPagerAdapter mWallpaperPagerAdapter;

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

        mWallpaperPagerAdapter = new WallpaperPagerAdapter(this, mImagens, getSupportActionBar());
        mWallpaperViewPager.setAdapter(mWallpaperPagerAdapter);
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

    public void onCheckPermissionsToSave() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                new MaterialDialog.Builder(this)
                        .title("Importante")
                        .content("Para você conseguir salvar as imagens no seu celular, é necessário que você autorize o App a fazer isso.")
                        .positiveText("Ok")
                        .negativeText("Não, Obrigado.")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                ActivityCompat.requestPermissions(WallpapersDetailActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                            }
                        })
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
        } else {
            salvarImagem();

        }
    }

    private void salvarImagem() {
        //BitmapDrawable drawable = (BitmapDrawable) mWallpaperPagerAdapter.getCurrentImageView().getDrawable();

        BasicImageDownloader downloader = new BasicImageDownloader(new BasicImageDownloader.OnImageLoaderListener() {
            @Override
            public void onError(BasicImageDownloader.ImageError error) {
                Log.i(TAG, "Error");
            }

            @Override
            public void onProgressChange(int percent) {

            }

            @Override
            public void onComplete(Bitmap result) {
                final Bitmap.CompressFormat mFormat = Bitmap.CompressFormat.JPEG;
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                File directory = cw.getDir("PopMovies Images", Context.MODE_PRIVATE);
                final File myImageFile = new File(directory, "image_" + mImagens.get(mWallpaperViewPager.getCurrentItem()).getImageID() + ".jpg");
                BasicImageDownloader.writeToDisk(myImageFile, result, new BasicImageDownloader.OnBitmapSaveListener() {
                    @Override
                    public void onBitmapSaved() {
                        Log.i(TAG, "Image saved as: " + myImageFile.getAbsolutePath());
                    }

                    @Override
                    public void onBitmapSaveError(BasicImageDownloader.ImageError error) {
                        Log.i(TAG, "Error code " + error.getErrorCode() + ": " +
                                error.getMessage());
                        error.printStackTrace();
                    }

                }, mFormat, false);

            }
        });

        downloader.download("http://image.tmdb.org/t/p/" + ImageSize.BACKDROP_1280.getSize() + "/" + mWallpaperPagerAdapter.getCurrentImage().getImagePath(), true);

//         new ImageSaver(this).
//                    setFileName(mWallpaperPagerAdapter.getCurrentImage().getImageID() + "_image.png").
//                    setDirectoryName("images").
//                    setExternal(false).
//                    save(drawable.getBitmap());

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    salvarImagem();
                break;
        }
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
                return true;
            case R.id.action_save:
                onCheckPermissionsToSave();
                return true;
            default:
                return false;
        }
    }

}
