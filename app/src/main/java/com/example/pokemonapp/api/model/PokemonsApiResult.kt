package com.example.pokemonapp.api.model

import com.example.pokemonapp.models.*
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue

data class PokemonsApiResult(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonResult>
)

data class PokemonResult(
    val name: String,
    val url: String
)

