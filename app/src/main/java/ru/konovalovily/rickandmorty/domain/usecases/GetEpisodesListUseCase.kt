package ru.konovalovily.rickandmorty.domain.usecases

import ru.konovalovily.rickandmorty.domain.LoadingRepository

class GetEpisodesListUseCase(private val repository: LoadingRepository) {

    suspend operator fun invoke() = repository.getEpisodesList()
}