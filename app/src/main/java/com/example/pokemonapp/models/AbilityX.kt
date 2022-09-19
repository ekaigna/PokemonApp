package com.example.pokemonapp.models
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class AbilityX(
    val name: String,
    val url: String
) : Parcelable