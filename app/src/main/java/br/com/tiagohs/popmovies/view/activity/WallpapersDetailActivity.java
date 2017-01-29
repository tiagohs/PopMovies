package br.com.tiagohs.popmovies.view.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ImageDTO;
import br.com.tiagohs.popmovies.util.PermissionUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.ServerUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.util.enumerations.ImageSize;
import br.com.tiagohs.popmovies.util.enumerations.TypeShowImage;
import br.com.tiagohs.popmovies.view.ViewPagerWallpapers;
import br.com.tiagohs.popmovies.view.adapters.WallpaperPagerAdapter;
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
        intent.putExtra(ARG_IMAGES, (ArrayList<ImageDTO>) mImagens);
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

        mImagens = (ArrayList<ImageDTO>) getIntent().getSerializableExtra(ARG_IMAGES);
        mImageCurrent = (ImageDTO) getIntent().getSerializableExtra(ARG_IMAGE_CURRENT);
        mPageTitle = getIntent().getStringExtra(ARG_WALLPAPERS_PAGE_TITLE);
        mPageSubtitle = getIntent().getStringExtra(ARG_WALLPAPERS_PAGE_SUBTITLE);
        mTypeShowImage = (TypeShowImage) getIntent().getSerializableExtra(ARG_TYPE_SHOW_IMAGE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mWallpaperPagerAdapter = new WallpaperPagerAdapter(this, mImagens, getSupportActionBar(), mTypeShowImage);
        mWallpaperViewPager.setAdapter(mWallpaperPagerAdapter);
        setCurrentImage();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBinder != null)
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
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    public void onSaveImageClicked() {

        mProgress.setVisibility(View.VISIBLE);

        if (!ServerUtils.isWifiConnected(this)) {
            new MaterialDialog.Builder(this)
                    .title("Importante")
                    .content("Você irá realizar um Download sem estar conectado ao Wi-fi, deseja continuar?")
                    .positiveText("Sim")
                    .negativeText("Não")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            onCheckPermissionsToSave();
                        }
                    })
                    .show();
        } else
            onCheckPermissionsToSave();
    }

    private void onCheckPermissionsToSave() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ViewUtils.createAlertDialogWithPositive(this, "Importante",
                        "Para você conseguir salvar as imagens no seu celular, é necessário que você autorize o App a fazer isso.",
                        new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            ActivityCompat.requestPermissions(WallpapersDetailActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                        }
                });
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }
        } else {
            salvarImagem();

        }
    }

    private void salvarImagem() {
        DownloadManager mgr = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        String path = "";
        String nameImage = "";

        if (mTypeShowImage.equals(TypeShowImage.SIMPLE_IMAGE)) {
            nameImage = PrefsUtils.getCurrentProfile(this).getProfileID() + "_perfil.jpg";
            path = mImagens.get(mWallpaperViewPager.getCurrentItem()).getImagePath();
        } else if (mTypeShowImage.equals(TypeShowImage.WALLPAPER_IMAGES)) {
            nameImage = mImagens.get(mWallpaperViewPager.getCurrentItem()).getMovieID() + "_image.jpg";
            path = "http://image.tmdb.org/t/p/" + ImageSize.BACKDROP_1280.getSize() + mImagens.get(mWallpaperViewPager.getCurrentItem()).getImagePath();
        }

        Uri downloadUri = Uri.parse(path);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("PopMovies")
                .setDescription("Baixando Wallpaper.")
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setDestinationInExternalPublicDir(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/PopMovies Images", nameImage);

        BroadcastReceiver onComplete = new BroadcastReceiver() {
            public void onReceive(Context ctxt, Intent intent) {
                Toast.makeText(WallpapersDetailActivity.this, "Imagem salva com sucesso", Toast.LENGTH_SHORT).show();
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

    private void share() {

        if (PermissionUtils.validatePermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, getString(R.string.permission_error_write_content)))
            createShareIntent();

    }

    public void createShareIntent() {
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/" + ImageSize.BACKDROP_1280.getSize() + mImagens.get(mWallpaperViewPager.getCurrentItem()).getImagePath()).into(new Target() {
            @Override public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("image/*");
                i.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap));
                startActivity(Intent.createChooser(i, "PopMovies - Compartilhar"));
            }

            @Override public void onBitmapFailed(Drawable errorDrawable) { }
            @Override public void onPrepareLoad(Drawable placeHolderDrawable) { }
        });
    }

    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file =  new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), mImagens.get(mWallpaperViewPager.getCurrentItem()).getMovieID() + "_image.jpg");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

}
