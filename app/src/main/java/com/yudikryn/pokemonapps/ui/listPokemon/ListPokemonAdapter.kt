package com.yudikryn.pokemonapps.ui.listPokemon

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yudikryn.pokemonapps.data.remote.model.Pokemon
import com.yudikryn.pokemonapps.databinding.ItemPokemonBinding
import java.util.Locale

class ListPokemonAdapter(private val onClick: (pokemon: Pokemon) -> Unit) : ListAdapter<Pokemon, ListPokemonAdapter.MyViewHolder>(DIFF_CALLBACK) {

    fun updateData(data: List<Pokemon>){
        val tempList : ArrayList<Pokemon> = arrayListOf()
        if (currentList.isNotEmpty()){
            tempList.addAll(currentList)
        }
        if (data.isNotEmpty()){
            tempList.addAll(data)
        }
        submitList(tempList.toMutableList())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon, onClick)
    }

    class MyViewHolder(private val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(pokemon: Pokemon, onClick: (pokemon: Pokemon) -> Unit) {
            binding.apply {

                tvName.text = pokemon.name.capitalize(Locale.getDefault())
                root.setOnClickListener {
                    onClick.invoke(pokemon)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Pokemon> =
            object : DiffUtil.ItemCallback<Pokemon>() {
                override fun areItemsTheSame(oldData: Pokemon, newData: Pokemon): Boolean {
                    return oldData.name == newData.name
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldData: Pokemon, newData: Pokemon): Boolean {
                    return oldData == newData
                }
            }
    }
}