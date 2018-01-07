package br.com.tiagohs.popmovies.ui.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.ui.contracts.LoginContract;
import br.com.tiagohs.popmovies.ui.tools.PopTextView;
import br.com.tiagohs.popmovies.util.FailureListener;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import butterknife.BindView;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements LoginContract.LoginView {
    private static final String TAG = SignUpActivity.class.getSimpleName();

    @BindView(R.id.error_confirm_password)          PopTextView mErrorConfirmPassword;
    @BindView(R.id.name_edit_text)                  EditText mNameEditText;
    @BindView(R.id.email_edit_text)                 EditText mEmailEditText;
    @BindView(R.id.password_edit_text)              EditText mPasswordEditText;
    @BindView(R.id.confirm_password_edit_text)      EditText mConfirmPasswordEditText;
    @BindView(R.id.btn_sign_up)                     Button mSignUpButton;

    @Inject
    LoginContract.LoginPresenter mPresenter;

    private FirebaseAuth mAuth;
    private FailureListener mFailureListener;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SignUpActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) getApplication()).getPopMoviesComponent().inject(this);
        mPresenter.onBindView(this);

        mAuth = FirebaseAuth.getInstance();
        mFailureListener = new FailureListener(this);

        onTextListener(mNameEditText);
        onTextListener(mPasswordEditText);
        onTextListener(mConfirmPasswordEditText);
        onTextListener(mEmailEditText);
    }

    private void onTextListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (areNotEmpty() && isPasswordAndConfirmPasswordMatch()) {
                    mSignUpButton.setVisibility(View.VISIBLE);
                } else {
                    mSignUpButton.setVisibility(View.GONE);
                }

                if (mPasswordEditText.getText().toString().length() > 0 && mConfirmPasswordEditText.getText().toString().length() > 0 &&
                    isPasswordAndConfirmPasswordMatch()) {
                    mErrorConfirmPassword.setVisibility(View.GONE);
                } else {
                    mErrorConfirmPassword.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private boolean areNotEmpty() {
        return mPasswordEditText.getText().toString().length() > 0 && mEmailEditText.getText().toString().length() > 0 &&
                mNameEditText.getText().toString().length() > 0  && mConfirmPasswordEditText.getText().toString().length() > 0;
    }

    private boolean isPasswordAndConfirmPasswordMatch() {
        return mPasswordEditText.getText().toString().equals(mConfirmPasswordEditText.getText().toString());
    }

    @OnClick(R.id.btn_sign_up)
    public void onClickSignUp() {
        final MaterialDialog dialog = showDialogProgress();

        final String email = mEmailEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();

                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            mPresenter.onSaveProfile(email, email, mNameEditText.getText().toString(), 0, null, null, UserDB.PHOTO_ONLINE);
                        } else {
                            mFailureListener.onFailure(task.getException());
                        }
                    }
                }).addOnFailureListener(SignUpActivity.this, mFailureListener);

        dialog.dismiss();
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected View.OnClickListener onSnackbarClickListener() {
        return  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSnackbar.dismiss();
            }
        };
    }

    @OnClick(R.id.sign_in)
    public void onClickSignIn() {
        startActivity(LoginActivity.newIntent(this));
    }

    @Override
    protected int getMenuLayoutID() {
        return 0;
    }

    @Override
    public void setProgressVisibility(int visibityState) {

    }

    @Override
    public boolean isAdded() {
        return isDestroyed();
    }

    @Override
    public void onSaveInSharedPreferences(ProfileDB profileDB) {
        PrefsUtils.setCurrentProfile(profileDB, this);
        PrefsUtils.setCurrentUser(profileDB.getUser(), this);
    }

    @Override
    public void onStartHome() {
        startActivity(HomeActivity.newIntent(this));
        finish();
    }
}
