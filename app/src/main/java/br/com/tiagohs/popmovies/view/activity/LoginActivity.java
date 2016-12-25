package br.com.tiagohs.popmovies.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONObject;

import java.util.Arrays;

import br.com.tiagohs.popmovies.BuildConfig;
import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.data.repository.ProfileRepository;
import br.com.tiagohs.popmovies.data.repository.UserRepository;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_facebook_button)
    LoginButton mLoginFacebookButton;

    @BindView(R.id.login_twitter_button)
    TwitterLoginButton mLoginTwitterButton;

    @BindView(R.id.title_app)
    TextView mTitle;

    private CallbackManager mFacebookCallbackManager;

    private UserRepository mUserRepository;
    private ProfileRepository mProfileRepository;

    private TwitterSession mSession;
    private TwitterAuthClient mAuthClient;
    private String mEmail;
    private String mNome;
    private String mPathFoto;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    public LoginActivity() {
        mUserRepository = new UserRepository(this);
        mProfileRepository = new ProfileRepository(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY, BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new Crashlytics());
        mAuthClient = new TwitterAuthClient();

        setContentView(R.layout.activity_login);

        if( PrefsUtils.getCurrentUser(LoginActivity.this) != null ) {
            startActivity(HomeActivity.newIntent(LoginActivity.this));
            finish();
        }

        ButterKnife.bind(this);

        initFacebook();
        initTwitter();
    }

    private void initFacebook() {
        mFacebookCallbackManager = CallbackManager.Factory.create();
        mLoginFacebookButton.registerCallback(mFacebookCallbackManager, facebookCallback());
        mLoginFacebookButton.setReadPermissions(Arrays.asList("user_photos", "email"));
        mLoginFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginFacebookButton.setVisibility(View.GONE);
                mLoginTwitterButton.setVisibility(View.GONE);
            }
        });
    }

    private void initTwitter() {
        mLoginTwitterButton.setCallback(twitterCallback());
        mLoginTwitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginTwitterButton.setVisibility(View.GONE);
                mLoginFacebookButton.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mTitle.setTypeface(Typeface.createFromAsset(getAssets(), "opensans.ttf"));
    }

    private FacebookCallback<LoginResult> facebookCallback() {


        return new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    setUserData(object.getString("name").toString(), object.getString("email").toString(), getString(R.string.facebook_photo, object.getString("id").toString()));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                startActivity(HomeActivity.newIntent(LoginActivity.this));
                                finish();
                            }

                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login pelo Facebook Cancelado.", Toast.LENGTH_SHORT).show();
                mLoginFacebookButton.setVisibility(View.VISIBLE);
                mLoginTwitterButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LoginActivity.this, "Erro ao realizar o Login pelo Facebook.", Toast.LENGTH_SHORT).show();
                mLoginFacebookButton.setVisibility(View.VISIBLE);
                mLoginTwitterButton.setVisibility(View.VISIBLE);
            }


        };
    }

    private Callback<TwitterSession> twitterCallback() {
        return new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                mSession = result.data;

                Call<User> call = Twitter.getApiClient(mSession).getAccountService()
                        .verifyCredentials(true, false);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void failure(TwitterException e) {
                        Toast.makeText(LoginActivity.this, "Erro ao realizar o Login pelo Twitter.", Toast.LENGTH_SHORT).show();
                        mLoginTwitterButton.setVisibility(View.VISIBLE);
                    }
                    @Override
                    public void success(Result<User> userResult) {


                        User user = userResult.data;
                        mNome = user.name;
                        mPathFoto = user.profileImageUrl;


                        mAuthClient.requestEmail(mSession, new Callback<String>() {
                            @Override
                            public void success(Result<String> result) {
                                Toast.makeText(LoginActivity.this, "Sucesso.", Toast.LENGTH_SHORT).show();
                                mEmail = result.data;
                                setUserData(mNome, mEmail, mPathFoto);
                                startActivity(HomeActivity.newIntent(LoginActivity.this));
                                finish();
                            }

                            @Override
                            public void failure(TwitterException exception) {
                                Toast.makeText(LoginActivity.this, "IH.", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(LoginActivity.this, "Erro ao realizar o Login pelo Twitter.", Toast.LENGTH_SHORT).show();
                mLoginTwitterButton.setVisibility(View.VISIBLE);
                mLoginFacebookButton.setVisibility(View.VISIBLE);
            }
        };
    }


    private void setUserData(String name, String email, String profileImage) {
        UserDB user = new UserDB();
        user.setEmail(email);
        user.setNome(name);
        user.setLogged(true);

        mUserRepository.saveUser(user);

        ProfileDB profile = new ProfileDB();
        profile.setUser(user);
        profile.setFotoPath(profileImage);

        long id = mProfileRepository.saveProfile(profile);
        user.setProfileID(id);
        profile.setProfileID(id);
        PrefsUtils.setCurrentUser(user, LoginActivity.this);
        PrefsUtils.setCurrentProfile(profile, LoginActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        mLoginTwitterButton.onActivityResult(requestCode, resultCode, data);
    }
}
