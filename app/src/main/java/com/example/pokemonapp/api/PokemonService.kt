package com.example.pokemonapp.api

import com.example.pokemonapp.models.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    fun listPokemons(@Query("limit") limit: Int): Call<Pokemon>

    @GET("pokemon/{number}")
    fun getPokemon(@Path("number") number: Int): Call<Pokemon>
}