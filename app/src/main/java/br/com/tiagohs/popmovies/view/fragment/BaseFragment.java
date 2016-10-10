package br.com.tiagohs.popmovies.view.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.PopMoviesComponent;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.util.ServerUtils;
import br.com.tiagohs.popmovies.view.activity.BaseActivity;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    protected MaterialDialog materialDialog;
    protected Snackbar mSnackbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getViewID(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injectViews(view);
    }

    private void injectViews(View view) {
        ButterKnife.bind(this, view);
    }

    protected abstract int getViewID();

    public void showDialogProgress() {
        materialDialog = new MaterialDialog.Builder(getActivity())
                .content(getString(R.string.progress_wait))
                .cancelable(false)
                .progress(true, 0)
                .show();
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

    public void onError(String msg) {
        mSnackbar = Snackbar
                .make(getCoordinatorLayout(), msg, Snackbar.LENGTH_INDEFINITE);

        mSnackbar.setActionTextColor(Color.RED);
        mSnackbar.show();
        mSnackbar.setAction(getString(R.string.tentar_novamente), onSnackbarClickListener());

    }

    protected abstract View.OnClickListener onSnackbarClickListener();

    public CoordinatorLayout getCoordinatorLayout() {
        return ((BaseActivity) getActivity()).getCoordinatorLayout();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (materialDialog != null) {
            hideDialogProgress();
        }
    }
}
