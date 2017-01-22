package br.com.tiagohs.popmovies.view.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.dto.ImageSaveDTO;
import br.com.tiagohs.popmovies.presenter.PerfilEditPresenter;
import br.com.tiagohs.popmovies.presenter.PerfilPresenter;
import br.com.tiagohs.popmovies.util.ImageIntentPicker;
import br.com.tiagohs.popmovies.util.ImageUtils;
import br.com.tiagohs.popmovies.util.LocaleUtils;
import br.com.tiagohs.popmovies.util.MovieUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import br.com.tiagohs.popmovies.view.PerfilEditView;
import butterknife.BindView;

public class PerfilEditActivity extends BaseActivity implements PerfilEditView {
    private static final String TAG = PerfilEditActivity.class.getSimpleName();

    private static final int PICK_IMAGE_PERFIL_ID = 234;

    @BindView(R.id.edit_name)
    EditText mEditName;

    @BindView(R.id.edit_birthday)
    EditText mEditBirthday;

    @BindView(R.id.edit_country)
    Spinner mEditCountry;

    @BindView(R.id.edit_gender)
    Spinner mEditGender;

    @BindView(R.id.edit_descricao)
    EditText mEditDescricao;

    @BindView(R.id.btn_photo)
    ImageButton mBtnPhoto;

    @BindView(R.id.image_perfil)
    ImageView mPhotoPerfil;

    @BindView(R.id.progress_perfil_foto)
    ProgressWheel mProgressFotoPerfil;

    @Inject
    PerfilEditPresenter mPresenter;

    private Calendar mBirthday;
    private String mCountry;
    private String mGender;
    private String mPhotoPerfilLocal;

    private DatePickerDialog.OnDateSetListener mDatePicker;
    private ArrayAdapter<String> mCountryAdapter;
    private ArrayAdapter<String> mGenderAdapter;

    public static Intent newIntent(Context context) {
        return  new Intent(context, PerfilEditActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        mPresenter.setContext(this);
        mPresenter.setView(this);

        setActivityTitle("Editar Perfil");

        updateBirthday();
        configureSpinnerCountry();
        configureSpinnerGender();
        configurePhotoPerfil();

        mPresenter.getProfileInfo();
    }

    private void configurePhotoPerfil() {
        mBtnPhoto.setOnClickListener(onClickBtnPhoto());
    }

    private View.OnClickListener onClickBtnPhoto() {
        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent chooseImageIntent = ImageIntentPicker.getPickImageIntent(PerfilEditActivity.this);
                startActivityForResult(chooseImageIntent, PICK_IMAGE_PERFIL_ID);
            }
        };
    }

    private void configureSpinnerCountry() {
        mCountryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, LocaleUtils.getAllCountrys());
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
        mGenderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.person_gender));
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
                new DatePickerDialog(PerfilEditActivity.this, mDatePicker, mBirthday
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
                ViewUtils.createToastMessage(this, "Dados salvos com sucesso.");
                finish();
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

    public void setPhoto(String path) {
        ImageUtils.load(this, path, R.drawable.placeholder_images_default, R.drawable.placeholder_images_default,  mPhotoPerfil, mProgressFotoPerfil);
    }

    @Override
    public void setLocalPhoto(Bitmap bitmap) {
        mPhotoPerfil.setImageBitmap(bitmap);
    }

    private void setEditTextValue(String text, EditText editText) {
        editText.setText(text);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case PICK_IMAGE_PERFIL_ID:
                if (data != null) {
                    ImageSaveDTO imageFromResult = ImageIntentPicker.getImageFromResult(this, resultCode, data);
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
    protected int getActivityBaseViewID() {
        return R.layout.activity_edit_perfil;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return null;
    }

    @Override
    protected int getMenuLayoutID() {
        return R.menu.menu_edit_perfil;
    }

    @Override
    public void setProgressVisibility(int visibityState) {

    }

    @Override
    public boolean isAdded() {
        return this != null;
    }
}
