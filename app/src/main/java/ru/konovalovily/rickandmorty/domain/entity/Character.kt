package ru.konovalovily.rickandmorty.domain.entity

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val episode: List<String>,
    val location: Location,
    val url: String
)
