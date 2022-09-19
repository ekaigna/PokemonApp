package com.example.pokemonapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class PokemonType(
    val name: String
) : Parcelable