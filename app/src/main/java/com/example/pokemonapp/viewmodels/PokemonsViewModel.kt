package com.example.pokemonapp.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonsViewModel @Inject constructor(
    application: Application,
) : AndroidViewModel(application) {


    fun applyQueries() : HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries["limit"] = "10"
        queries["offset"] = "0"

        return queries
    }

}
