package com.example.pokemonapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonapp.databinding.PokemonRowLayoutBinding
import com.example.pokemonapp.models.Pokemon

class PokemonAdapter() : RecyclerView.Adapter<PokemonAdapter.MyViewHolder>() {

    private var pokemons = emptyList<Pokemon>()

    class MyViewHolder(private val binding: PokemonRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pokemon?) {

            binding.pokemon = item
            binding.executePendingBindings()
            //if (item != null) {
            //Glide.with(itemView.context).load(item.imageUrl).into(binding.pokemonImageView)
            //}
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PokemonRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    return MyViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentPokemon = pokemons[position]
            holder.bind(currentPokemon)
    }
    override fun getItemCount(): Int {
        return pokemons.size
    }

    fun setData(newData: List<Pokemon>){
        pokemons = newData
        notifyDataSetChanged()
    }





}