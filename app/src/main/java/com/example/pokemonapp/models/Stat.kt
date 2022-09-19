package com.example.pokemonapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Stat (
    val base_stat: Int,
    val effort: Int,
    val stat: @RawValue StatX
        ) : Parcelable


