package com.example.pokemonapp.api

import com.example.pokemonapp.util.Constants
import com.example.pokemonapp.api.model.PokemonApiResult
import com.example.pokemonapp.api.model.PokemonsApiResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PokemonRepository {
    private val service: PokemonService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(PokemonService::class.java)
    }

    fun listPokemons(limit: Int = 4): PokemonsApiResult? {
        val call = service.listPokemons(limit)

    //    call.enqueue(object: Callback<PokemonsApiResult> {
        //        override fun onResponse(
        //            call: Call<PokemonsApiResult>,
        //           response: Response<PokemonsApiResult>
        //       ) {
        //           if(response.isSuccessful){
                    //               val body = response.body()
//
        //               body?.results.let{
        //                   it?.get(0)?.let { it1 -> Log.e("POKEMON_API", it1.name) }
        //               }
        //            }
        //            Log.e("POKEMON_API", "Pokemon List Loading")
        //        }
//
        //       override fun onFailure(call: Call<PokemonsApiResult>, t: Throwable) {
        //          Log.e("POKEMON_API", "Error loading pokemons list.", t)
        //      }
//
        //  })
        return call.execute().body()
    }

    fun getPokemon(number: Int): PokemonApiResult? {
        val call = service.getPokemon(number)

        return call.execute().body()
    }
}