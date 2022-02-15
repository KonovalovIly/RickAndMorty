package ru.konovalovily.rickandmorty.data.di

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.konovalovily.rickandmorty.data.CharacterApi
import ru.konovalovily.rickandmorty.domain.entity.Constants

val networkModule = module {
    factory<Retrofit> { provideRetrofit() }
    single<CharacterApi> { provideNoteServiceApi(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideNoteServiceApi(retrofit: Retrofit): CharacterApi =
    retrofit.create(CharacterApi::class.java)