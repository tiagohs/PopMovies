package br.com.tiagohs.popmovies.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.ui.view.fragment.PersonDetailResumoFragment;
import br.com.tiagohs.popmovies.ui.view.fragment.PersonDetailCarrerFragment;

/**
 * Created by Tiago Henrique on 04/09/2016.
 */
public class TabPersonCarrerAdapter extends FragmentStatePagerAdapter {
    private static final int TAB_RESUMO = 0;
    private static final int TAB_CARREIRA = 1;

    private String[] mTabNames;
    private PersonInfo mPersonInfo;

    public TabPersonCarrerAdapter(FragmentManager fm, String[] tabNames, PersonInfo personInfo) {
        super(fm);

        mTabNames = tabNames;
        mPersonInfo = personInfo;
    }

    @Override
    public Fragment getItem(int tabSelecionada) {

        switch(tabSelecionada) {
            case TAB_RESUMO:
                return PersonDetailResumoFragment.newInstance(mPersonInfo);
            case TAB_CARREIRA:
                return PersonDetailCarrerFragment.newInstance(mPersonInfo);
            default:
                return PersonDetailResumoFragment.newInstance(mPersonInfo);
        }
    }

    @Override
    public int getCount() {
        return mTabNames.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabNames[position];
    }
}
