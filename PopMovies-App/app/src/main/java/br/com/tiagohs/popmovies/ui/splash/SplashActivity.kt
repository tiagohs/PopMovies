package br.com.tiagohs.popmovies.ui.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.tiagohs.core.navigation.destinations.HomeDestination
import br.com.tiagohs.core.navigation.destinations.LogInDestination
import br.com.tiagohs.popmovies.MainActivity
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashActivity: ComponentActivity() {

    private val viewModel: SplashViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.checkSignIn { isLogged ->
                    val startScreenRoute = if (isLogged) {
                        HomeDestination.route
                    } else {
                        LogInDestination.route
                    }

                    startActivity(MainActivity.newIntent(this@SplashActivity, startScreenRoute))
                    finish()
                }
            }
        }
    }
}