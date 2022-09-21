package com.example.pokemonapp.bindingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.bumptech.glide.Glide
import com.example.pokemonapp.R
import com.example.pokemonapp.models.Pokemon
import com.example.pokemonapp.models.PokemonType
import com.example.pokemonapp.ui.fragments.pokemons.PokemonFragmentDirections

class PokemonRowBinding {

    companion object {

        @BindingAdapter("formatId")
        @JvmStatic
        fun formatId(textView: TextView, id: Int) {
            val formattedNumber = "Nº " + id.toString().padStart(3, '0')
            textView.text = formattedNumber
        }

        @BindingAdapter("formatName")
        @JvmStatic
        fun formatName(textView: TextView, name: String) {
            textView.text = name.substring(0, 1).uppercase() + name.substring(1).lowercase();
        }

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
        fun loadImageFromUrl(imageView: ImageView, id: Int) {
            val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
            Glide.with(imageView).load(imageUrl).into(imageView)
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



