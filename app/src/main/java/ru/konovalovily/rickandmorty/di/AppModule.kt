package ru.konovalovily.rickandmorty.di

import org.koin.dsl.module
import ru.konovalovily.rickandmorty.presentation.characters.CharacterListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import ru.konovalovily.rickandmorty.data.LoadingRepositoryImpl
import ru.konovalovily.rickandmorty.domain.LoadingRepository
import ru.konovalovily.rickandmorty.presentation.characterdetail.CharacterDescriptionViewModel
import ru.konovalovily.rickandmorty.presentation.episode.EpisodeListViewModel

val appModule = module {

    factory<LoadingRepository> { LoadingRepositoryImpl(get()) }

    viewModel<CharacterListViewModel> { CharacterListViewModel(get()) }

    viewModel<CharacterDescriptionViewModel> { CharacterDescriptionViewModel(get()) }

    viewModel<EpisodeListViewModel> { EpisodeListViewModel(get()) }
}