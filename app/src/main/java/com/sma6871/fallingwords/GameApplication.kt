package com.sma6871.fallingwords

import android.app.Application
import com.sma6871.fallingwords.di.appModule
import com.sma6871.fallingwords.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GameApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin()

    }

    private fun initKoin() {
        startKoin {
            androidContext(this@GameApplication)
            modules(viewModelModule + appModule)
        }
    }
}