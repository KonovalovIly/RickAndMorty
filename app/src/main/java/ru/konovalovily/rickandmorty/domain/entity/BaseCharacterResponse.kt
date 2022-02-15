package ru.konovalovily.rickandmorty.domain.entity

data class BaseCharacterResponse(
    val info: Info,
    val results: List<Character>
)
