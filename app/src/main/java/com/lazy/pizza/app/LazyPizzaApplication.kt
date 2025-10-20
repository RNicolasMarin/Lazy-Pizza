package com.lazy.pizza.app

import android.app.Application
import com.lazy.pizza.core.data.di.coreDataModule
import com.lazy.pizza.detail.presentation.di.detailViewModelModule
import com.lazy.pizza.home.presentation.di.homeViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class LazyPizzaAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@LazyPizzaAppApplication)
            modules(
                homeViewModelModule,
                detailViewModelModule,
                coreDataModule
            )
        }
    }
}