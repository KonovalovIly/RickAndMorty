package ru.konovalovily.rickandmorty.domain.usecases

import ru.konovalovily.rickandmorty.domain.LoadingRepository

class GetCharacterListUseCase(private val repository: LoadingRepository) {

    suspend operator fun invoke(page: Int) = repository.getCharactersList(page)
}