package com.example.pokemonapp.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.pokemonapp.api.model.PokemonsApiResult
import com.example.pokemonapp.data.Repository
import com.example.pokemonapp.models.Ability
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

    var searchedPokemonsResponse: MutableLiveData<NetworkResult<Pokemon>> = MutableLiveData()

    fun getPokemons(queries: Map<String, String>) = viewModelScope.launch {
        val pokemons: Response<PokemonsApiResult> = repository.remote.getPokemons(queries)
        if (pokemons.isSuccessful) {
            var pokemonList = pokemons.body()?.results?.map { pokemon ->
                val number = pokemon.url.replace("https://pokeapi.co/api/v2/pokemon/", "")
                    .replace("/", "")
                    .toInt()
                val pokemon: Response<Pokemon> = repository.remote.getPokemon(number)
                pokemon.body()?.let {
                    Pokemon(
                        it.id,
                        it.name,
                        it.types.map { type ->
                            type
                        },
                        it.weight,
                        it.height,
                        it.abilities.map { ability -> ability},
                        it.species,
                        it.stats.map { stat ->
                            stat
                        })
                }}
            pokemonsResponse.value = NetworkResult.Success(pokemonList)
        }
        else pokemonsResponse.value = NetworkResult.Error("Pokemons not found.")
    }

    fun searchPokemon(name: String) = viewModelScope.launch {
        searchPokemonSafeCall(name)
    }

    private suspend fun searchPokemonSafeCall(name: String) {
        searchedPokemonsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getPokemonByName(name)
                searchedPokemonsResponse.value = handlePokemonsResponse(response)
            } catch (e: Exception) {
                searchedPokemonsResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            searchedPokemonsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun handlePokemonsResponse(response: Response<Pokemon>): NetworkResult<Pokemon> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()?.equals("undefined") == true -> {
                return NetworkResult.Error("Pokemon not found.")
            }
            response.isSuccessful -> {
                val pokemon = response.body()
                return NetworkResult.Success(pokemon!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
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