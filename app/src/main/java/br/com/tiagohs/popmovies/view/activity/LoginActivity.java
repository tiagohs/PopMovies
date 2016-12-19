package br.com.tiagohs.popmovies.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import br.com.tiagohs.popmovies.R;
import br.com.tiagohs.popmovies.data.repository.ProfileRepository;
import br.com.tiagohs.popmovies.data.repository.UserRepository;
import br.com.tiagohs.popmovies.model.db.ProfileDB;
import br.com.tiagohs.popmovies.model.db.UserDB;
import br.com.tiagohs.popmovies.util.PrefsUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_facebook_button)
    LoginButton mLoginButton;

    @BindView(R.id.title_app)
    TextView mTitle;

    private CallbackManager mCallbackManager;

    private UserRepository mUserRepository;
    private ProfileRepository mProfileRepository;

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
        setContentView(R.layout.activity_login);

        if( PrefsUtils.getCurrentUser(LoginActivity.this) != null ) {
            startActivity(HomeActivity.newIntent(LoginActivity.this));
            finish();
        }

        ButterKnife.bind(this);

        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton.registerCallback(mCallbackManager, facebookCallback());
        mLoginButton.setReadPermissions(Arrays.asList("user_photos", "email"));
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginButton.setVisibility(View.GONE);
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
                                    UserDB user = new UserDB();
                                    user.setEmail(object.getString("email").toString());
                                    user.setNome(object.getString("name").toString());
                                    user.setLogged(true);

                                    mUserRepository.saveUser(user);

                                    ProfileDB profile = new ProfileDB();
                                    profile.setUser(user);
                                    profile.setFotoPath(getString(R.string.facebook_photo, object.getString("id").toString()));

                                    long id = mProfileRepository.saveProfile(profile);
                                    user.setProfileID(mProfileRepository.findProfileByUserEmail(user.getEmail()).getProfileID());
                                    profile.setProfileID(id);
                                    PrefsUtils.setCurrentUser(user, LoginActivity.this);
                                    PrefsUtils.setCurrentProfile(profile, LoginActivity.this);
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
                mLoginButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LoginActivity.this, "Erro ao realizar o Login pelo Facebook.", Toast.LENGTH_SHORT).show();
                mLoginButton.setVisibility(View.VISIBLE);
            }


        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
