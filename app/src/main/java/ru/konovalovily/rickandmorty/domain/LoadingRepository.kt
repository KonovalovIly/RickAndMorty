package ru.konovalovily.rickandmorty.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.konovalovily.rickandmorty.domain.entity.Character
import ru.konovalovily.rickandmorty.domain.entity.Episode

interface LoadingRepository {

    suspend fun getCharactersList(): Flow<PagingData<Character>>

    suspend fun getCharacter(characterId: Int): Character

    suspend fun getEpisodesList(): Flow<PagingData<Episode>>

}