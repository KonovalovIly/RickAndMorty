package ru.konovalovily.rickandmorty.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.konovalovily.rickandmorty.data.paging.EpisodePagingSource
import ru.konovalovily.rickandmorty.data.paging.RickAndMortyPagingSource
import ru.konovalovily.rickandmorty.domain.LoadingRepository
import ru.konovalovily.rickandmorty.domain.entity.Character
import ru.konovalovily.rickandmorty.domain.entity.Constants.PAGE_SIZE
import ru.konovalovily.rickandmorty.domain.entity.Episode

class LoadingRepositoryImpl(private val api: CharacterApi) : LoadingRepository {

    override suspend fun getCharactersList(): Flow<PagingData<Character>> =
        Pager(PagingConfig(PAGE_SIZE)) {
            RickAndMortyPagingSource(api)
        }.flow

    override suspend fun getCharacter(characterId: Int): Character =
        api.getCharacterDetail(characterId)

    override suspend fun getEpisodesList(): Flow<PagingData<Episode>> =
        Pager(PagingConfig(PAGE_SIZE)) {
            EpisodePagingSource(api)
        }.flow

}