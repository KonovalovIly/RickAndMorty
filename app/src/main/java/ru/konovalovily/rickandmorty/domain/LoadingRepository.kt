package ru.konovalovily.rickandmorty.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.konovalovily.rickandmorty.domain.entity.Character

interface LoadingRepository {

    suspend fun getCharactersList(): Flow<PagingData<Character>>

    suspend fun getCharacter(characterId: Int): Character

}