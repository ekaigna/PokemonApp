package com.example.pokemonapp.data.network

import com.example.pokemonapp.api.model.PokemonApiResult
import com.example.pokemonapp.api.model.PokemonsApiResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemons(
        @QueryMap queries: Map<String, String>
    ): Response<PokemonsApiResult>

    @GET("pokemon/{number}")
    suspend fun getPokemon(
        @Path("number") number: Int
    ): Response<PokemonApiResult>
}