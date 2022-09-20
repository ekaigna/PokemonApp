package com.example.pokemonapp.data

import com.example.pokemonapp.api.model.PokemonsApiResult
import com.example.pokemonapp.data.network.PokemonApi
import com.example.pokemonapp.models.Pokemon
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val pokemonApi: PokemonApi
){
    suspend fun getPokemons(queries: Map<String, String>): Response<PokemonsApiResult> {
        return pokemonApi.getPokemons(queries)
    }

    suspend fun getPokemon(number: Int): Response<Pokemon> {
        return pokemonApi.getPokemon(number)
    }

    suspend fun getPokemonByName(name: String): Response<Pokemon> {
        return pokemonApi.getPokemonByName(name)
    }
}