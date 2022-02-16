package ru.konovalovily.rickandmorty.domain.usecases

import ru.konovalovily.rickandmorty.domain.LoadingRepository

class GetCharacterUseCase(private val repository: LoadingRepository) {

    suspend operator fun invoke(characterId: Int) = repository.getCharacter(characterId)
}