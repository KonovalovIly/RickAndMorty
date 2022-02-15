package ru.konovalovily.rickandmorty.data

import retrofit2.http.GET
import retrofit2.http.Query
import ru.konovalovily.rickandmorty.domain.entity.BaseCharacterResponse

interface CharacterApi {

    @GET("character/")
    suspend fun getCharacters(
        @Query("page") page: Int,
    ): BaseCharacterResponse

}