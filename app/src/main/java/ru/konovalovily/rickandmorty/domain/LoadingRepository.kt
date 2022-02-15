package ru.konovalovily.rickandmorty.domain

import ru.konovalovily.rickandmorty.domain.entity.Character

interface LoadingRepository {

    suspend fun getCharactersList(page: Int): List<Character>
}