package com.example.pokemonapp.data

import android.util.Log
import com.example.pokemonapp.api.PokemonRepository
import com.example.pokemonapp.api.model.PokemonApiResult
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

    suspend fun getPokemon(number: Int): Response<PokemonApiResult> {
        return pokemonApi.getPokemon(number)
    }
}