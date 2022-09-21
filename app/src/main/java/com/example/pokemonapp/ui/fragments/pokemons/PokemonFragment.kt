package com.example.pokemonapp.ui.fragments.pokemons

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
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
import com.example.pokemonapp.ui.MainActivity




@AndroidEntryPoint
class PokemonFragment : Fragment(), SearchView.OnQueryTextListener, SearchView.OnCloseListener {

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

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.pokemons_menu, menu)

                val search = menu.findItem(R.id.menu_search)
                val searchView = search.actionView as? SearchView
                searchView?.setOnQueryTextListener(this@PokemonFragment)
                searchView?.setOnCloseListener(this@PokemonFragment)
                }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

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

    private fun searchApiData(searchQuery: String) {
        showShimmerEffect()
        mainViewModel.searchPokemon(searchQuery)
        mainViewModel.searchedPokemonsResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    val listOfPokemons: MutableList<Pokemon> = mutableListOf()
                    val pokemon = response.data
                    if (pokemon != null) {
                        listOfPokemons.add(pokemon)
                        Log.d("pokemonSearchMutable", listOfPokemons.toString())
                    }
                    listOfPokemons?.let { mAdapter.setData(it as List<Pokemon>) }
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


    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    override fun onClose(): Boolean {
        requestApiData()
        return false
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