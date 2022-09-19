package com.example.pokemonapp.ui.fragments.pokemons

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonapp.Constants
import com.example.pokemonapp.adapters.PokemonAdapter
import com.example.pokemonapp.api.PokemonRepository
import com.example.pokemonapp.databinding.FragmentPokemonBinding
import com.example.pokemonapp.models.Pokemon
import com.example.pokemonapp.models.PokemonType

class PokemonFragment : Fragment() {

    private var _binding: FragmentPokemonBinding? = null
    private val binding get() = _binding!!

    //private val mAdapter by lazy { PokemonAdapter() }

    //val charmander = Pokemon(
     //   "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/132.png",
       // "1",
    //"Charmander",
    //        listOf(
    //            PokemonType("Fire")
    //        )
    //    )
    //
    //    val pokemons = listOf(
    //        charmander,
    //        charmander,
    //        charmander,
    //        charmander
    //    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPokemonBinding.inflate(inflater, container, false)
        //binding.recyclerView.showShimmer();
        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        showShimmerEffect()
        Thread(Runnable {
            loadPokemons()
    }).start()
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun loadPokemons() {
        val pokemonsApiResult = PokemonRepository.listPokemons()

        pokemonsApiResult?.results?.let {

            val pokemons: List<Pokemon?> = it.map { pokemonResult ->

                val number = pokemonResult.url.replace("https://pokeapi.co/api/v2/pokemon/", "")
                    .replace("/", "")
                    .toInt()
                val pokemonApiResult = PokemonRepository.getPokemon(number)

                pokemonApiResult?.let {
                    Pokemon(pokemonApiResult.id,
                        pokemonApiResult.name,
                        pokemonApiResult.types.map { type ->
                        type.type
                    },
                        pokemonApiResult.weight,
                        pokemonApiResult.height,
                        pokemonApiResult.abilities.map { ability ->
                           ability.ability
                       },
                        pokemonApiResult.species,
                        pokemonApiResult.stats.map { stat ->
                            stat
                        })
                }
            }
            Log.d("POKEMON_API", pokemonsApiResult.toString())

                binding.recyclerView.post {
                    hideShimmerEffect()
                    binding.recyclerView.adapter = PokemonAdapter(pokemons)
                binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())}
        }


    }

}