package com.example.pokemonapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonapp.databinding.PokemonRowLayoutBinding
import com.example.pokemonapp.models.Pokemon

class PokemonAdapter(private var pokemons: List<Pokemon?>) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    //private var pokemons = emptyList<Pokemon>()

    class ViewHolder(private val binding: PokemonRowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Pokemon?) {

            binding.pokemon = item
            binding.executePendingBindings()
            if (item != null) {
                Glide.with(itemView.context).load(item.imageUrl).into(binding.pokemonImageView)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PokemonRowLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder.from(parent)
}
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pokemons[position]
        if (item != null) {
            holder.bindView(item)
        }
    }
    override fun getItemCount(): Int {
        return pokemons.size
    }

    fun setData(newData: List<Pokemon?>){
        pokemons = newData
        notifyDataSetChanged()
    }





}