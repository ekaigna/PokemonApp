package com.example.pokemonapp.ui.fragments.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.pokemonapp.util.Constants.Companion.POKEMON_RESULT_KEY
import com.example.pokemonapp.databinding.FragmentOverviewBinding
import com.example.pokemonapp.formatName
import com.example.pokemonapp.models.Pokemon

class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val myBundle: Pokemon = args!!.getParcelable<Pokemon>(POKEMON_RESULT_KEY) as Pokemon
        Log.d("POKEMON_BUNDLE", myBundle.toString())
       // binding.mainImageView.load(myBundle.imageUrl)
        Glide.with(binding.mainImageView.context).load(myBundle.imageUrl).into(binding.mainImageView)
        binding.titleTextView.text = myBundle.formattedName
        binding.subtitleTextView.text = myBundle.formattedNumber
        binding.type1TextView.text = formatName(myBundle.types[0].type.name)

        binding.weightNumber.text = myBundle.weight.toString() + " hg"
        binding.heightNumber.text = myBundle.height.toString() + " dm"

        binding.speciesName.text = formatName(myBundle.species.name)

        if (myBundle.abilities.size > 1) {
            binding.habilitiesList.text = myBundle.abilities.joinToString{formatName(it.ability.name)}

        } else {
            binding.habilitiesList.text = formatName(myBundle.abilities[0].ability.name)
        }

        if(myBundle.types.size > 1) {
            binding.type2TextView.visibility = View.VISIBLE
            binding.type2TextView.text = formatName(myBundle.types[1].type.name)

        } else {
            binding.type2TextView.visibility = View.GONE
        }
        //binding.timeTextView.text = myBundle.types[1].name
        //PokemonRowBinding.parseHtml(binding.summaryTextView, myBundle.summary)

        return binding.root
    }
}