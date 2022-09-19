package com.example.pokemonapp.models


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize

data class Ability(
    val ability: @RawValue AbilityX,
    val isHidden: Boolean,
    val slot: Int
) : Parcelable