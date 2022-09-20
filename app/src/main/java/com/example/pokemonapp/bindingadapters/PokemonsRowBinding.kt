package com.example.pokemonapp.bindingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.pokemonapp.R
import com.example.pokemonapp.models.Pokemon
import com.example.pokemonapp.models.PokemonType
import com.example.pokemonapp.ui.fragments.pokemons.PokemonFragmentDirections

class PokemonRowBinding {

    companion object {

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(pokemonRowLayout: ConstraintLayout, pokemon: Pokemon) {
            Log.d("onRecipeClickListener", "CALLED")
           pokemonRowLayout.setOnClickListener {
                try {
                    val action = PokemonFragmentDirections.actionPokemonFragmentToDetailsActivity(pokemon)
                  pokemonRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onRecipeClickListener", e.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                error(R.drawable.ic_sad)
            }
        }

        @BindingAdapter("loadType2")
        @JvmStatic
        fun loadType2(textView: TextView, typesList: List<PokemonType>) {
            if(typesList.size > 1) {
                textView.visibility = View.VISIBLE
                textView.text = typesList[1].type.name
            } else {
                textView.visibility = View.GONE
            }
            }
        }
    }



