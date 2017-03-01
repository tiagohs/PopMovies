package br.com.tiagohs.popmovies.ui.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.ui.adapters.TabPerfilAdapter;
import butterknife.BindView;

/**
 * Created by Tiago on 18/12/2016.
 */

public class PerfilFragment extends BaseFragment {

    @BindView(R.id.tabLayout)           TabLayout mTabPerfil;
    @BindView(R.id.perfil_view_pager)   ViewPager mPerfilViewPager;

    public static PerfilFragment newInstance() {
        PerfilFragment perfilFragment = new PerfilFragment();
        return perfilFragment;
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_perfil;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSnackbar.dismiss();
            }
        };
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPerfilViewPager.setAdapter(new TabPerfilAdapter(getChildFragmentManager()));
        mTabPerfil.setupWithViewPager(mPerfilViewPager);
        mTabPerfil.getTabAt(0).setIcon(ViewUtils.getDrawableFromResource(getActivity(), R.drawable.ic_movie));
        mTabPerfil.getTabAt(1).setIcon(ViewUtils.getDrawableFromResource(getActivity(), R.drawable.ic_estatisticas));
    }
}
