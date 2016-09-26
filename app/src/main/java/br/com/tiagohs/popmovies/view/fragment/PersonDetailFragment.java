package br.com.tiagohs.popmovies.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.person.PersonInfo;
import br.com.tiagohs.popmovies.view.adapters.TabPersonCarrerAdapter;
import butterknife.BindView;

public class PersonDetailFragment extends BaseFragment {
    public static final String ARG_PERSON = "person";

    @BindView(R.id.tabLayout)
    TabLayout mTabCarrer;

    @BindView(R.id.carrer_view_pager)
    ViewPager mCarrerViewPager;

    private PersonInfo mPersonInfo;

    public static PersonDetailFragment newInstance(PersonInfo person) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_PERSON, person);

        PersonDetailFragment personDetailFragment = new PersonDetailFragment();
        personDetailFragment.setArguments(bundle);

        return personDetailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPersonInfo = (PersonInfo) getArguments().getSerializable(ARG_PERSON);
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_person_detail;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCarrerViewPager.setAdapter(new TabPersonCarrerAdapter(getChildFragmentManager(), getResources().getStringArray(R.array.person_carrer_tab_array), mPersonInfo));
        mTabCarrer.setupWithViewPager(mCarrerViewPager);
    }
}
