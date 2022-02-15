package ru.konovalovily.rickandmorty.data.di

import org.koin.dsl.module
import ru.konovalovily.rickandmorty.presentation.CharacterListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import ru.konovalovily.rickandmorty.data.LoadingRepositoryImpl
import ru.konovalovily.rickandmorty.domain.LoadingRepository

val appModule = module {

    factory<LoadingRepository> { LoadingRepositoryImpl(get()) }

    viewModel<CharacterListViewModel> { CharacterListViewModel(get()) }
}