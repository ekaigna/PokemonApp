package com.example.pokemonapp.ui.fragments.pokemons.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pokemonapp.util.Constants.Companion.DEFAULT_POKEMON_TYPE
import com.example.pokemonapp.databinding.PokemonBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import java.util.*

class PokemonBottomSheet : BottomSheetDialogFragment() {

    private var _binding: PokemonBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var pokemonTypeChip = DEFAULT_POKEMON_TYPE
    private var pokemonTypeChipId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PokemonBottomSheetBinding.inflate(inflater, container, false)

        binding.pokemonTypeChipGroup.setOnCheckedStateChangeListener{
            group, selectedChipId ->
            val chip = group.findViewById<Chip>(selectedChipId.first())
            val selectedPokemonType = chip.text.toString().lowercase(Locale.ROOT)
            pokemonTypeChip = selectedPokemonType
            pokemonTypeChipId = selectedChipId.first()
        }

        binding.applyBtn.setOnClickListener {
            TODO("chamada do filtro")
        }


        return binding.root


    }

}