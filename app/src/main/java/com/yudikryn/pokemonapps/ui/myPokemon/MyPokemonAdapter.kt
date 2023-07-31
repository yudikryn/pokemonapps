package com.yudikryn.pokemonapps.ui.myPokemon

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.yudikryn.pokemonapps.data.local.entity.PokemonEntity
import com.yudikryn.pokemonapps.databinding.ItemMyPokemonBinding
import java.util.Locale

class MyPokemonAdapter(private val onClick: (pokemon: PokemonEntity, action: String, position: Int) -> Unit) : ListAdapter<PokemonEntity, MyPokemonAdapter.MyViewHolder>(DIFF_CALLBACK) {

    fun deleteItem(position: Int){
        currentList.drop(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, currentList.size)
    }

    fun updateItem(pokemon: PokemonEntity, position: Int){
        currentList[position].name = pokemon.name
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMyPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon, position, onClick)
    }

    class MyViewHolder(private val binding: ItemMyPokemonBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(pokemon: PokemonEntity, position: Int, onClick: (pokemon: PokemonEntity, action: String, position: Int) -> Unit) {
            binding.apply {

                tvName.text = pokemon.name.capitalize(Locale.getDefault())

                binding.tvRelease.setOnClickListener {
                    onClick.invoke(pokemon, ACTION_RELEASE, position)
                }

                binding.tvRename.setOnClickListener {
                    onClick.invoke(pokemon, ACTION_RENAME, position)
                }

                root.setOnClickListener {
                    onClick.invoke(pokemon, ACTION_DETAIL, position)
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<PokemonEntity> =
            object : DiffUtil.ItemCallback<PokemonEntity>() {
                override fun areItemsTheSame(oldData: PokemonEntity, newData: PokemonEntity): Boolean {
                    return oldData.name == newData.name
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldData: PokemonEntity, newData: PokemonEntity): Boolean {
                    return oldData == newData
                }
            }


        const val ACTION_RELEASE = "release"
        const val ACTION_RENAME = "rename"
        const val ACTION_DETAIL = "detail"
    }
}