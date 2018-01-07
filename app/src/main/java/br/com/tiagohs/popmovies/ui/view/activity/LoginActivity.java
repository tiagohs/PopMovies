package br.com.tiagohs.popmovies.ui.view.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.Arrays;

import javax.inject.Inject;

import br.com.tiagohs.popmovies.App;
import br.com.tiagohs.popmovies.BuildConfig;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.ui.contracts.LoginContract;
import br.com.tiagohs.popmovies.util.AnimationsUtils;
import br.com.tiagohs.popmovies.util.EmptyUtils;
import br.com.tiagohs.popmovies.util.FailureListener;
import br.com.tiagohs.popmovies.util.PermissionUtils;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import br.com.tiagohs.popmovies.util.ViewUtils;
import butterknife.BindView;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends BaseActivity implements LoginContract.LoginView {
    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final int GOOGLE_SIGN_IN = 100;

    private static final String USER_ID_KEY = "id";
    private static final String USER_NAME_KEY = "name";
    private static final String USER_EMAIL_KEY = "email";
    private static final String USER_PHOTO_KEY = "user_photos";

    @BindView(R.id.email_edit_text)                     EditText mEmailEditText;
    @BindView(R.id.password_edit_text)                  EditText mPasswordEditText;
    @BindView(R.id.login_facebook_button)               Button mLoginFacebookButton;
    @BindView(R.id.login_twitter_button)                Button mLoginTwitterButton;
    @BindView(R.id.login_google_button)                 Button mLoginGoogleButton;
    @BindView(R.id.btn_login)                           Button mLoginButton;
    @BindView(R.id.login_facebook_button_original)      LoginButton mLoginFacebookOriginalButton;
    @BindView(R.id.login_twitter_button_original)       TwitterLoginButton mLoginTwitterOriginalButton;
    @BindView(R.id.title_app)                           TextView mTitle;

    @Inject
    LoginContract.LoginPresenter mPresenter;

    private CallbackManager mFacebookCallbackManager;
    private TwitterSession mSession;
    private FailureListener mFailureListener;

    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;

    private FirebaseAuth mAuth;

    private String mUsername;
    private String mEmail;
    private String mName;
    private String mPathFoto;
    private String mToken;

    private int mTypeLogin;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PermissionUtils.validate(this);

        onStartConfigurateLoginSDKs();

        if (EmptyUtils.isNotNull(PrefsUtils.getCurrentUser(LoginActivity.this))) {
            startActivity(HomeActivity.newIntent(LoginActivity.this));
            finish();
        }

        ((App) getApplication()).getPopMoviesComponent().inject(this);

        super.onCreate(savedInstanceState);
        mPresenter.onBindView(this);

        mFailureListener = new FailureListener(this);

        onTextListener(mPasswordEditText);
        onTextListener(mEmailEditText);

        onSetupFacebook();
        onSetupTwitter();
        onSetupGoogle();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onUnbindView();
    }

    @Override
    protected int getActivityBaseViewID() {
        return R.layout.activity_login;
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

    @Override
    protected int getMenuLayoutID() {
        return 0;
    }

    private void onTextListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mPasswordEditText.getText().toString().length() > 0 && mEmailEditText.getText().toString().length() > 0) {
                    mLoginButton.setVisibility(View.VISIBLE);
                } else {
                    mLoginButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    public void onStartConfigurateLoginSDKs() {
        mAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new Crashlytics());
    }

    private void onSetupFacebook() {
        mFacebookCallbackManager = CallbackManager.Factory.create();
        mLoginFacebookOriginalButton.registerCallback(mFacebookCallbackManager, facebookCallback());
        mLoginFacebookOriginalButton.setReadPermissions(Arrays.asList(USER_EMAIL_KEY, USER_PHOTO_KEY));

        mLoginFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginFacebookOriginalButton.performClick();
                setGoogleButtonVisibility(View.GONE);
                setFacebookButtonVisibility(View.GONE);
                setTwitterButtonVisibility(View.GONE);
            }
        });
    }

    private void onSetupTwitter() {
        mLoginTwitterOriginalButton.setCallback(twitterCallback());
        mLoginTwitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginTwitterOriginalButton.performClick();
                setGoogleButtonVisibility(View.GONE);
                setTwitterButtonVisibility(View.GONE);
                setFacebookButtonVisibility(View.GONE);
            }
        });
    }

    private void onSetupGoogle() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        ViewUtils.createToastMessage(LoginActivity.this, getString(R.string.login_google_error));

                        setGoogleButtonVisibility(View.VISIBLE);
                        setTwitterButtonVisibility(View.VISIBLE);
                        setFacebookButtonVisibility(View.VISIBLE);
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mLoginGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mGoogleApiClient.isConnected())
                    mGoogleApiClient.disconnect();

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN);

                setGoogleButtonVisibility(View.GONE);
                setTwitterButtonVisibility(View.GONE);
                setFacebookButtonVisibility(View.GONE);
            }
        });
    }

    @OnClick(R.id.btn_login)
    public void onSignIn() {
        final String email = mEmailEditText.getText().toString();
        final String password = mPasswordEditText.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:sucess");

                            mUsername = mEmail = mName = email;
                            mToken = mAuth.getCurrentUser().getUid();

                            setUserData();

                        } else {
                            mFailureListener.onFailure(task.getException());
                        }
                    }
                }).addOnFailureListener(LoginActivity.this, mFailureListener);
    }

    private FacebookCallback<LoginResult> facebookCallback() {
        mTypeLogin = UserDB.LOGIN_FACEBOOK;

        return new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                mToken = loginResult.getAccessToken().getToken();

                AuthCredential credential = FacebookAuthProvider.getCredential(mToken);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    mName = user.getDisplayName();
                                    mUsername = mEmail = user.getEmail();

                                    mPathFoto = user.getPhotoUrl().getPath();

                                    setUserData();
                                } else {
                                    mFailureListener.onFailure(task.getException());

                                    setGoogleButtonVisibility(View.VISIBLE);
                                    setTwitterButtonVisibility(View.VISIBLE);
                                    setFacebookButtonVisibility(View.VISIBLE);
                                }

                            }
                        }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mFailureListener.onFailure(e);

                        setGoogleButtonVisibility(View.VISIBLE);
                        setTwitterButtonVisibility(View.VISIBLE);
                        setFacebookButtonVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onCancel() {
                ViewUtils.createToastMessage(LoginActivity.this, getString(R.string.login_facebook_cancelled));
                setGoogleButtonVisibility(View.VISIBLE);
                setFacebookButtonVisibility(View.VISIBLE);
                setTwitterButtonVisibility(View.VISIBLE);
            }

            @Override
            public void onError(FacebookException e) {
                ViewUtils.createToastMessage(LoginActivity.this, getString(R.string.login_facebook_error));
                setGoogleButtonVisibility(View.VISIBLE);
                setFacebookButtonVisibility(View.VISIBLE);
                setTwitterButtonVisibility(View.VISIBLE);
            }

        };
    }

    private Callback<TwitterSession> twitterCallback() {
        mTypeLogin = UserDB.LOGIN_TWITTER;

        return new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                mSession = result.data;
                mToken = mSession.getAuthToken().token;

                AuthCredential credential = TwitterAuthProvider.getCredential(
                        mSession.getAuthToken().token,
                        mSession.getAuthToken().secret);

                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    mName = user.getDisplayName();
                                    mUsername = mEmail = user.getEmail();

                                    mPathFoto = user.getPhotoUrl().getPath();

                                    setUserData();
                                } else {
                                    mFailureListener.onFailure(task.getException());

                                    setGoogleButtonVisibility(View.VISIBLE);
                                    setTwitterButtonVisibility(View.VISIBLE);
                                    setFacebookButtonVisibility(View.VISIBLE);
                                }

                            }
                        }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mFailureListener.onFailure(e);

                        setGoogleButtonVisibility(View.VISIBLE);
                        setTwitterButtonVisibility(View.VISIBLE);
                        setFacebookButtonVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void failure(TwitterException exception) {
                ViewUtils.createToastMessage(LoginActivity.this, getString(R.string.login_twitter_error));
                setGoogleButtonVisibility(View.VISIBLE);
                setTwitterButtonVisibility(View.VISIBLE);
                setFacebookButtonVisibility(View.VISIBLE);
            }
        };
    }

    private void googleCallback(GoogleSignInResult result) {
        if (result.isSuccess()) {
            final GoogleSignInAccount acct = result.getSignInAccount();

            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();

                                mUsername = mEmail = user.getEmail();
                                mName = user.getDisplayName();
                                mToken = acct.getIdToken();

                                if (acct.getPhotoUrl() != null)
                                    mPathFoto = acct.getPhotoUrl().toString();

                                setUserData();
                            } else {
                                mFailureListener.onFailure(task.getException());

                                setGoogleButtonVisibility(View.VISIBLE);
                                setTwitterButtonVisibility(View.VISIBLE);
                                setFacebookButtonVisibility(View.VISIBLE);
                            }
                        }
                    }).addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mFailureListener.onFailure(e);

                    setGoogleButtonVisibility(View.VISIBLE);
                    setTwitterButtonVisibility(View.VISIBLE);
                    setFacebookButtonVisibility(View.VISIBLE);
                }
            });

        } else {
            ViewUtils.createToastMessage(LoginActivity.this, getString(R.string.login_google_error));

            setGoogleButtonVisibility(View.VISIBLE);
            setTwitterButtonVisibility(View.VISIBLE);
            setFacebookButtonVisibility(View.VISIBLE);
        }
    }

    private void setUserData() {
        mPresenter.onSaveProfile(mUsername, mEmail, mName, mTypeLogin, mToken, mPathFoto, UserDB.PHOTO_ONLINE);
    }

    public void onStartHome() {
        startActivity(HomeActivity.newIntent(LoginActivity.this));
        finish();
    }

    public void onSaveInSharedPreferences(ProfileDB profileDB) {
        PrefsUtils.setCurrentProfile(profileDB, this);
        PrefsUtils.setCurrentUser(profileDB.getUser(), this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        mLoginTwitterOriginalButton.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            googleCallback(result);
        }
    }

    private void setButtonVisibility(Button button, final int visibility) {

        if (visibility == View.VISIBLE)
            AnimationsUtils.createShowCircularReveal(button, onAnimatedEnd(button, visibility));
        else {
            AnimationsUtils.createHideCircularReveal(button, onAnimatedEnd(button, visibility));
        }
    }


    private void setFacebookButtonVisibility(final int visibility) {

        if (visibility == View.VISIBLE)
            AnimationsUtils.createShowCircularReveal(mLoginFacebookButton, onAnimatedEnd(mLoginFacebookButton, visibility));
        else {
            AnimationsUtils.createHideCircularReveal(mLoginFacebookButton, onAnimatedEnd(mLoginFacebookButton, visibility));
        }
    }

    private void setTwitterButtonVisibility(int visibility) {

        if (visibility == View.VISIBLE)
            AnimationsUtils.createShowCircularReveal(mLoginTwitterButton, onAnimatedEnd(mLoginTwitterButton, visibility));
        else
            AnimationsUtils.createHideCircularReveal(mLoginTwitterButton, onAnimatedEnd(mLoginTwitterButton, visibility));
    }

    private void setGoogleButtonVisibility(int visibility) {

        if (visibility == View.VISIBLE)
            AnimationsUtils.createShowCircularReveal(mLoginGoogleButton, onAnimatedEnd(mLoginGoogleButton, visibility));
        else
            AnimationsUtils.createHideCircularReveal(mLoginGoogleButton, onAnimatedEnd(mLoginGoogleButton, visibility));
    }

    private Animator.AnimatorListener onAnimatedEnd(final Button button, final int visibility) {
        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                button.setVisibility(visibility);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };
    }

    @OnClick(R.id.sign_up)
    public void onSignUpLinkClick() {
        startActivity(SignUpActivity.newIntent(this));
    }

    @Override
    public void setProgressVisibility(int visibityState) {

    }

    @Override
    public boolean isAdded() {
        return isDestroyed();
    }
}
