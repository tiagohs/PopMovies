package br.com.tiagohs.popmovies.ui.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.ui.callbacks.PerfilEditCallbacks;
import br.com.tiagohs.popmovies.ui.view.fragment.PerfilEditFragment;

public class PerfilEditActivity extends BaseActivity implements PerfilEditCallbacks {
    private static final String TAG = PerfilEditActivity.class.getSimpleName();

    public static Intent newIntent(Context context) {
        return new Intent(context, PerfilEditActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActivityTitle(getString(R.string.title_activity_edit_perfil));

        startFragment(R.id.content_fragment, PerfilEditFragment.newInstance());
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_default;
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
    public void onFinishActivity() {
        finish();
    }
}
