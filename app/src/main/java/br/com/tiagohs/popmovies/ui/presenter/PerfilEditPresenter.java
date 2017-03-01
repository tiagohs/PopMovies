package br.com.tiagohs.popmovies.ui.presenter;


import java.util.Calendar;

import br.com.tiagohs.popmovies.ui.contracts.PerfilEditContract;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.util.ViewUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class PerfilEditPresenter implements PerfilEditContract.PerfilEditPresenter {
    private static final String TAG = PerfilEditPresenter.class.getSimpleName();

    private PerfilEditContract.PerfilEditView mPerfilEditView;
    private PerfilEditContract.PerfilEditInterceptor mInterceptor;

    private CompositeDisposable mSubscribers;
    private ProfileDB mProfileDB;

    public PerfilEditPresenter(PerfilEditContract.PerfilEditInterceptor interceptor, CompositeDisposable subscribers) {
        mInterceptor = interceptor;
        mSubscribers = subscribers;
    }

    @Override
    public void onBindView(PerfilEditContract.PerfilEditView view) {
        mPerfilEditView = view;
    }

    @Override
    public void onUnbindView() {
        mSubscribers.clear();
        mPerfilEditView = null;
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
        if (!ViewUtils.isEmptyValue(mProfileDB.getUser().getNome())) {
            mPerfilEditView.setName(mProfileDB.getUser().getNome());
        }

        if (!ViewUtils.isEmptyValue(mProfileDB.getDescricao())) {
            mPerfilEditView.setDescricao(mProfileDB.getDescricao());
        }

        if (!ViewUtils.isEmptyValue(mProfileDB.getCountry())) {
            mPerfilEditView.setCountry(mProfileDB.getCountry());
        }

        if (mProfileDB.getBirthday() != null)
            mPerfilEditView.setBirthday(mProfileDB.getBirthday());

        if (mProfileDB.getUser().getTypePhoto() == UserDB.PHOTO_ONLINE)
            mPerfilEditView.setPhoto(mProfileDB.getUser().getPicturePath());
        else if (mProfileDB.getUser().getTypePhoto() == UserDB.PHOTO_LOCAL)
            mPerfilEditView.setLocalPhoto();
    }

    @Override
    public void save(String name, Calendar birthday, String country, String gender, String descricao, String photo) {

        if (!ViewUtils.isEmptyValue(name))
            mProfileDB.getUser().setNome(name);

        if (birthday != null)
            mProfileDB.setBirthday(birthday);

        if (!ViewUtils.isEmptyValue(country))
            mProfileDB.setCountry(country);

        if (!ViewUtils.isEmptyValue(gender))
            mProfileDB.setGenrer(gender);

        if (!ViewUtils.isEmptyValue(descricao))
            mProfileDB.setDescricao(descricao);

        if (!ViewUtils.isEmptyValue(photo)) {
            mProfileDB.getUser().setLocalPicture(photo);
            mProfileDB.getUser().setTypePhoto(UserDB.PHOTO_LOCAL);
        }

        mSubscribers.add(mInterceptor.saveProfile(mProfileDB)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe());

    }

}
