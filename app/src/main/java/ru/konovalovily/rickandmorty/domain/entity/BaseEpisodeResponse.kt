package ru.konovalovily.rickandmorty.domain.entity

data class BaseEpisodeResponse(
    val info: Info,
    val results: List<Episode>
)
