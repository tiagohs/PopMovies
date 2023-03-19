package br.com.tiagohs.popmovies

import android.app.Application
import br.com.tiagohs.popmovies.di.Injection
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MainApplication)
            modules(Injection.modules)
        }
    }
}