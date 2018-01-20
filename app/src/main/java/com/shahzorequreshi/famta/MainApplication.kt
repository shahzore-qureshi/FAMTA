package com.shahzorequreshi.famta

import android.app.Application
import com.shahzorequreshi.famta.dagger.AppComponent
import com.shahzorequreshi.famta.dagger.AppModule
import com.shahzorequreshi.famta.dagger.DaggerAppComponent

/**
 * Application class is inherited in order to initialize Dagger.
 */
class MainApplication : Application() {
    companion object {
        private lateinit var mComponent: AppComponent
        val component: AppComponent get() = mComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        mComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}