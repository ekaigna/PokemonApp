package com.example.pokemonapp.ui.fragments.pokemons

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonapp.viewmodels.MainViewModel
import com.example.pokemonapp.R
import com.example.pokemonapp.adapters.PokemonAdapter
import com.example.pokemonapp.databinding.FragmentPokemonBinding
import com.example.pokemonapp.models.Pokemon
import com.example.pokemonapp.util.NetworkResult
import com.example.pokemonapp.viewmodels.PokemonsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonFragment : Fragment() {

    private val args by navArgs<PokemonFragmentArgs>()

    private var _binding: FragmentPokemonBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var pokemonsViewModel: PokemonsViewModel
    private val mAdapter by lazy { PokemonAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        pokemonsViewModel = ViewModelProvider(requireActivity())[PokemonsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPokemonBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        setupRecyclerView()
        requestApiData()

        binding.pokemonFab.setOnClickListener {
        findNavController().navigate(R.id.action_pokemonFragment_to_pokemonBottomSheet)
        }

        return binding.root
    }

    private fun requestApiData() {
        Log.d("RecipesFragment", "requestApiData called!")
        mainViewModel.getPokemons(pokemonsViewModel.applyQueries())
        mainViewModel.pokemonsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it as List<Pokemon>) }
                    Log.d("pokemons data", response.data.toString())
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = mAdapter
       binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
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

}