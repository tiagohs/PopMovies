package br.com.tiagohs.popmovies.ui.view.fragment;


import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.dragger.components.PopMoviesComponent;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.ui.view.activity.BaseActivity;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.ServerUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    protected MaterialDialog materialDialog;
    protected Snackbar mSnackbar;
    protected ProfileDB mProfileDB;

    protected Unbinder mBinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getViewID(), container, false);
        injectViews(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProfileDB = PrefsUtils.getCurrentProfile(getActivity());
    }

    private void injectViews(View view) {
        mBinder = ButterKnife.bind(this, view);
    }

    protected abstract int getViewID();

    protected boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    public void showDialogProgress() {
        materialDialog = new MaterialDialog.Builder(getActivity())
                .content(getString(R.string.progress_wait))
                .cancelable(false)
                .progress(true, 0)
                .show();
    }

    @Override
    public void onPause() {
        ((App) getActivity().getApplication()).clearCache();
        super.onPause();
    }

    public void hideDialogProgress() {
        materialDialog.dismiss();
    }

    public boolean isInternetConnected() {
        return ServerUtils.isNetworkConnected(getActivity());
    }

    protected PopMoviesComponent getApplicationComponent() {
        return ((App) getActivity().getApplication()).getPopMoviesComponent();
    }

    protected void startFragment(int fragmentID, Fragment fragment) {
        FragmentManager fm = getChildFragmentManager();
        Fragment f = fm.findFragmentById(fragmentID);

        if (null == f) {
            fm.beginTransaction()
                    .add(fragmentID, fragment)
                    .commitAllowingStateLoss();
        } else {
            fm.beginTransaction()
                    .replace(fragmentID, fragment)
                    .commitAllowingStateLoss();
        }
    }

    public void openUrl(String url) {

        if (!EmptyUtils.isEmpty(url)) {
            try {
                Uri urlUri = Uri.parse(url);
                CustomTabsIntent intent = new CustomTabsIntent.Builder()
                        .setToolbarColor(ViewUtils.getColorFromResource(getContext(), R.color.colorPrimary))
                        .setShowTitle(true)
                        .setStartAnimations(getContext(), R.anim.slide_in_right, R.anim.slide_out_left)
                        .setExitAnimations(getContext(), android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .build();
                intent.launchUrl(getContext(), urlUri);
            } catch (Exception ex) {

            }
        }
    }

    public void onError(int msgID) {
        mSnackbar = Snackbar
                .make(getCoordinatorLayout(), msgID, Snackbar.LENGTH_LONG);

        mSnackbar.setActionTextColor(Color.RED);
        mSnackbar.show();
        mSnackbar.setAction(getString(R.string.tentar_novamente).toUpperCase(), onSnackbarClickListener());
    }

    public void onErrorNoConnection() {
        onError(R.string.error_no_internet);
    }

    public void onErrorInServer() {
        onError(R.string.error_no_server);
    }

    public void onErrorUnexpected() {
        onError(R.string.erro_unexpected);
    }

    protected abstract View.OnClickListener onSnackbarClickListener();

    public CoordinatorLayout getCoordinatorLayout() {
        return ((BaseActivity) getActivity()).getCoordinatorLayout();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if (EmptyUtils.isNotNull(mBinder))
            mBinder.unbind();

        if (EmptyUtils.isNotNull(materialDialog))
            hideDialogProgress();

        if (EmptyUtils.isNotNull(mSnackbar))
            mSnackbar.dismiss();


    }
}
