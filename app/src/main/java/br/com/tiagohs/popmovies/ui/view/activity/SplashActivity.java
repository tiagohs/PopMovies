package br.com.tiagohs.popmovies.ui.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(LoginActivity.newIntent(this));
        finish();
    }
}
