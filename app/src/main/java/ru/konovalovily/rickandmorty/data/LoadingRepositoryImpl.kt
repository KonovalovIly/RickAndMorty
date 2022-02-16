package ru.konovalovily.rickandmorty.data

import ru.konovalovily.rickandmorty.domain.LoadingRepository
import ru.konovalovily.rickandmorty.domain.entity.Character

class LoadingRepositoryImpl(private val api: CharacterApi) : LoadingRepository {


    override suspend fun getCharactersList(page: Int): List<Character> =
        api.getCharacters(page).results

    override suspend fun getCharacter(characterId: Int): Character =
        api.getCharacterDetail(characterId)

}