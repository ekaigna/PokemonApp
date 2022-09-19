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

data class PokemonApiResult(
    val id: Int,
    val name: String,
    val types: List<PokemonTypeSlot>,
    val weight: Int,
    val height: Int,
    val abilities: List<Ability>,
    val species: Species,
    val stats: List<Stat>
)
data class PokemonTypeSlot(
    val slot: Int,
    val type: PokemonType
)

