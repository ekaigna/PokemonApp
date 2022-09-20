package com.example.pokemonapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.pokemonapp.api.model.PokemonApiResult
import com.example.pokemonapp.api.model.PokemonsApiResult
import com.example.pokemonapp.data.Repository
import com.example.pokemonapp.models.Pokemon
import com.example.pokemonapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** RETROFIT */
    var pokemonsResponse: MutableLiveData<NetworkResult<List<Pokemon?>?>> = MutableLiveData()

    fun getPokemons(queries: Map<String, String>) = viewModelScope.launch {
        val pokemons: Response<PokemonsApiResult> = repository.remote.getPokemons(queries)
        if (pokemons.isSuccessful) {
            var pokemonList = pokemons.body()?.results?.map { pokemon ->
                val number = pokemon.url.replace("https://pokeapi.co/api/v2/pokemon/", "")
                    .replace("/", "")
                    .toInt()
                val pokemon: Response<PokemonApiResult> = repository.remote.getPokemon(number)
                pokemon.body()?.let {
                    Pokemon(
                        it.id,
                        it.name,
                        it.types.map { type ->
                            type.type
                        },
                        it.weight,
                        it.height,
                        it.abilities.map { ability ->
                            ability.ability
                        },
                        it.species,
                        it.stats.map { stat ->
                            stat
                        })
                }}
            pokemonsResponse.value = NetworkResult.Success(pokemonList)
        }
        else pokemonsResponse.value = NetworkResult.Error("Pokemons not found.")
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }





}