package com.example.semana09firebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.semana09firebase.R
import com.example.semana09firebase.model.PokemonModel
import com.squareup.picasso.Picasso

class PokemonAdapter(private var pokemonList: List<PokemonModel>)
    : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener ? =null
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val ivPokemon: ImageView = itemView.findViewById(R.id.ivPokemon)
        private val tvPokemonName: TextView = itemView.findViewById(R.id.tvPokemonName)
        fun bind(pokemon: PokemonModel){
            tvPokemonName.text = pokemon.name.toString()
            Picasso.get().load(pokemon.imageUrl).into(ivPokemon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemonItem = pokemonList[position]
        holder.bind(pokemonItem)
    }
    fun setData(pokemonList: List<PokemonModel>){
        this.pokemonList = pokemonList
        notifyDataSetChanged()
    }
    fun setOnItemClickListener(listener: OnItemClickListener){
        onItemClickListener = listener
    }
    interface OnItemClickListener {
        fun onItemClick(pokemon: PokemonModel)
    }
}