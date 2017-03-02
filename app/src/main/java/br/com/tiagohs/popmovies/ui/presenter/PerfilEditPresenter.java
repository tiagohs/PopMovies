package br.com.tiagohs.popmovies.ui.presenter;


import java.util.Calendar;

import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.ui.contracts.PerfilEditContract;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class PerfilEditPresenter extends BasePresenter<PerfilEditContract.PerfilEditView, PerfilEditContract.PerfilEditInterceptor> implements PerfilEditContract.PerfilEditPresenter {
    private static final String TAG = PerfilEditPresenter.class.getSimpleName();

    private ProfileDB mProfileDB;

    public PerfilEditPresenter(PerfilEditContract.PerfilEditInterceptor interceptor, CompositeDisposable subscribers) {
        super(interceptor, subscribers);
    }

    public void getProfileInfo(String username) {

        mSubscribers.add(mInterceptor.findProfileByUserUsername(username)
                          .observeOn(AndroidSchedulers.mainThread())
                          .subscribe(new Consumer<ProfileDB>() {
                              @Override
                              public void accept(ProfileDB profileDB) throws Exception {
                                  mProfileDB = profileDB;
                                  onPerfilReceived();
                              }
                          }));
    }

    private void onPerfilReceived() {
        if (!EmptyUtils.isEmpty(mProfileDB.getUser().getNome())) {
            mView.setName(mProfileDB.getUser().getNome());
        }

        if (!EmptyUtils.isEmpty(mProfileDB.getDescricao())) {
            mView.setDescricao(mProfileDB.getDescricao());
        }

        if (!EmptyUtils.isEmpty(mProfileDB.getCountry())) {
            mView.setCountry(mProfileDB.getCountry());
        }

        if (EmptyUtils.isNotNull(mProfileDB.getBirthday()))
            mView.setBirthday(mProfileDB.getBirthday());

        if (mProfileDB.getUser().getTypePhoto() == UserDB.PHOTO_ONLINE)
            mView.setPhoto(mProfileDB.getUser().getPicturePath(), mProfileDB.getUser().getNome());
        else if (mProfileDB.getUser().getTypePhoto() == UserDB.PHOTO_LOCAL)
            mView.setLocalPhoto();
    }

    @Override
    public void save(String name, Calendar birthday, String country, String gender, String descricao, String photo) {

        if (!EmptyUtils.isEmpty(name))
            mProfileDB.getUser().setNome(name);

        if (EmptyUtils.isNotNull(birthday))
            mProfileDB.setBirthday(birthday);

        if (!EmptyUtils.isEmpty(country))
            mProfileDB.setCountry(country);

        if (!EmptyUtils.isEmpty(gender))
            mProfileDB.setGenrer(gender);

        if (!EmptyUtils.isEmpty(descricao))
            mProfileDB.setDescricao(descricao);

        if (!EmptyUtils.isEmpty(photo)) {
            mProfileDB.getUser().setLocalPicture(photo);
            mProfileDB.getUser().setTypePhoto(UserDB.PHOTO_LOCAL);
        }

        mSubscribers.add(mInterceptor.saveProfile(mProfileDB)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe());

    }

}
