package ru.konovalovily.rickandmorty.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.konovalovily.rickandmorty.domain.entity.BaseCharacterResponse
import ru.konovalovily.rickandmorty.domain.entity.Character

interface CharacterApi {

    @GET("character/")
    suspend fun getCharacters(
        @Query("page") page: Int,
    ): BaseCharacterResponse

    @GET("character/{characterId}")
    suspend fun getCharacterDetail(
        @Path("characterId") characterId: Int,
    ): Character
}