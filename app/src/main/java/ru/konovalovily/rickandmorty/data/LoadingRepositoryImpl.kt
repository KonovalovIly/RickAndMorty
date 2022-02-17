package ru.konovalovily.rickandmorty.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.konovalovily.rickandmorty.data.paging.RickAndMortyPagingSource
import ru.konovalovily.rickandmorty.domain.LoadingRepository
import ru.konovalovily.rickandmorty.domain.entity.Character

class LoadingRepositoryImpl(private val api: CharacterApi) : LoadingRepository {

    override suspend fun getCharactersList(): Flow<PagingData<Character>> =
        Pager(PagingConfig(1)) {
            RickAndMortyPagingSource(api)
        }.flow

    override suspend fun getCharacter(characterId: Int): Character =
        api.getCharacterDetail(characterId)

}