package br.com.tiagohs.popmovies.ui.view.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ImageSaveDTO;
import br.com.tiagohs.popmovies.ui.callbacks.PerfilEditCallbacks;
import br.com.tiagohs.popmovies.ui.contracts.PerfilEditContract;
import br.com.tiagohs.popmovies.ui.tools.ImageIntentPicker;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.LocaleUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import butterknife.BindView;


public class PerfilEditFragment extends BaseFragment implements PerfilEditContract.PerfilEditView {

    private static final int PICK_IMAGE_PERFIL_ID = 234;

    @BindView(R.id.edit_name)                   EditText mEditName;
    @BindView(R.id.edit_birthday)               EditText mEditBirthday;
    @BindView(R.id.edit_country)                Spinner mEditCountry;
    @BindView(R.id.edit_gender)                 Spinner mEditGender;
    @BindView(R.id.edit_descricao)              EditText mEditDescricao;
    @BindView(R.id.btn_photo)                   ImageButton mBtnPhoto;
    @BindView(R.id.image_circle)                ImageView mPhotoPerfil;
    @BindView(R.id.progress_image_circle)       ProgressWheel mProgressFotoPerfil;

    @Inject
    PerfilEditContract.PerfilEditPresenter mPresenter;

    private Calendar mBirthday;
    private String mCountry;
    private String mGender;
    private String mPhotoPerfilLocal;

    private DatePickerDialog.OnDateSetListener mDatePicker;
    private ArrayAdapter<String> mCountryAdapter;
    private ArrayAdapter<String> mGenderAdapter;

    private PerfilEditCallbacks mCallbacks;

    public static PerfilEditFragment newInstance() {
        PerfilEditFragment perfilEditFragment = new PerfilEditFragment();
        return perfilEditFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (PerfilEditCallbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getApplicationComponent().inject(this);

        mPresenter.onBindView(this);

        updateBirthday();
        configureSpinnerCountry();
        configureSpinnerGender();
        configurePhotoPerfil();

        mPresenter.getProfileInfo(PrefsUtils.getCurrentProfile(getContext()).getUser().getUsername());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onUnbindView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_perfil, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void configurePhotoPerfil() {
        mBtnPhoto.setOnClickListener(onClickBtnPhoto());
    }

    private View.OnClickListener onClickBtnPhoto() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivityForResult(ImageIntentPicker.getPickImageIntent(getContext()), PICK_IMAGE_PERFIL_ID);
            }
        };
    }

    private void configureSpinnerCountry() {
        mCountryAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, LocaleUtils.getAllCountrys());
        mCountryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEditCountry.setAdapter(mCountryAdapter);
        mEditCountry.setOnItemSelectedListener(onCountrySelected());
    }

    private AdapterView.OnItemSelectedListener onCountrySelected() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCountry = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private void configureSpinnerGender() {
        mGenderAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.person_gender));
        mGenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEditGender.setAdapter(mGenderAdapter);
        mEditGender.setOnItemSelectedListener(onGenderSelected());
    }

    private AdapterView.OnItemSelectedListener onGenderSelected() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    private void updateBirthday() {
        mBirthday = Calendar.getInstance();

        mDatePicker = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mBirthday.set(Calendar.YEAR, year);
                mBirthday.set(Calendar.MONTH, monthOfYear);
                mBirthday.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                formatBirthday();
            }
        };

        mEditBirthday.setOnClickListener(onClickBirthday());
    }

    private View.OnClickListener onClickBirthday() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), mDatePicker, mBirthday
                        .get(Calendar.YEAR), mBirthday.get(Calendar.MONTH),
                        mBirthday.get(Calendar.DAY_OF_MONTH)).show();
            }
        };
    }

    private void formatBirthday() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", new Locale(LocaleUtils.getLocaleCountryISO()));
        setEditTextValue(formatter.format(mBirthday.getTime()), mEditBirthday);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_edit_save:
                mPresenter.save(mEditName.getText().toString(), mBirthday, mCountry, mGender, mEditDescricao.getText().toString(), mPhotoPerfilLocal);
                ViewUtils.createToastMessage(getContext(), getString(R.string.sucess_data_saved));
                mCallbacks.onFinishActivity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setName(String name) {
        setEditTextValue(name, mEditName);
    }

    public void setBirthday(Calendar birthday) {
        mBirthday = birthday;
        formatBirthday();
    }

    public void setCountry(String country) {
        int spinnerPosition = mCountryAdapter.getPosition(country);
        mEditCountry.setSelection(spinnerPosition);
    }

    public void setDescricao(String descricao) {
        setEditTextValue(descricao, mEditDescricao);
    }

    public void setPhoto(String path, String name) {
        ImageUtils.load(getContext(), path, name,  mPhotoPerfil, mProgressFotoPerfil);
    }

    @Override
    public void setLocalPhoto() {
        mPhotoPerfil.setImageBitmap(ImageUtils.getBitmapFromPath(mProfileDB.getUser().getLocalPicture(), getContext()));
    }

    private void setEditTextValue(String text, EditText editText) {
        editText.setText(text);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case PICK_IMAGE_PERFIL_ID:
                if (resultCode == getActivity().RESULT_OK) {
                    ImageSaveDTO imageFromResult = ImageIntentPicker.getImageFromResult(getContext(), resultCode, data);
                    mPhotoPerfilLocal = imageFromResult.getPath();
                    mPhotoPerfil.setImageBitmap(imageFromResult.getBitmap());
                }

                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    protected int getViewID() {
        return R.layout.fragment_edit_perfil;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    public void setProgressVisibility(int visibityState) {

    }
}
