package ru.konovalovily.rickandmorty.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.konovalovily.rickandmorty.domain.entity.BaseCharacterResponse
import ru.konovalovily.rickandmorty.domain.entity.BaseEpisodeResponse
import ru.konovalovily.rickandmorty.domain.entity.Character
import ru.konovalovily.rickandmorty.domain.entity.Constants.CHARACTER_ID_RESPONSE
import ru.konovalovily.rickandmorty.domain.entity.Constants.CHARACTER_RESPONSE
import ru.konovalovily.rickandmorty.domain.entity.Constants.EPISODE_RESPONSE
import ru.konovalovily.rickandmorty.domain.entity.Constants.PAGE_RESPONSE

interface CharacterApi {

    @GET(CHARACTER_RESPONSE)
    suspend fun getCharacters(
        @Query(PAGE_RESPONSE) page: Int,
    ): BaseCharacterResponse

    @GET("$CHARACTER_RESPONSE/{characterId}")
    suspend fun getCharacterDetail(
        @Path(CHARACTER_ID_RESPONSE) characterId: Int,
    ): Character

    @GET(EPISODE_RESPONSE)
    suspend fun getEpisodes(
        @Query(PAGE_RESPONSE) page: Int,
    ): BaseEpisodeResponse

}