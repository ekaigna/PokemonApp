package com.example.pokemonapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Pokemon(
    val id: Int,
    val name: String,
    val types: @RawValue List<PokemonType>,
    val weight: Int,
    val height: Int,
    val abilities: @RawValue List<Ability>,
    val species: @RawValue Species,
    val stats: @RawValue List<Stat>

) : Parcelable {
    val formattedNumber = "Nº " + id.toString().padStart(3, '0')
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
    val formattedName = name.substring(0, 1).uppercase() + name.substring(1).lowercase();
}


