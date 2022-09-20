package com.example.pokemonapp.data.network

import com.example.pokemonapp.api.model.PokemonsApiResult
import com.example.pokemonapp.models.Pokemon
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
    ): Response<Pokemon>

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String
    ): Response<Pokemon>

}