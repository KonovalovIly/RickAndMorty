package ru.konovalovily.rickandmorty


import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.konovalovily.rickandmorty.data.di.appModule
import ru.konovalovily.rickandmorty.data.di.networkModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, appModule))
        }
    }
}