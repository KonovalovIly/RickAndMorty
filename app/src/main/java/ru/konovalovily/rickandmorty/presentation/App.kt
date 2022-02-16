package ru.konovalovily.rickandmorty.presentation


import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.konovalovily.rickandmorty.di.appModule
import ru.konovalovily.rickandmorty.di.networkModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, appModule))
        }
    }
}