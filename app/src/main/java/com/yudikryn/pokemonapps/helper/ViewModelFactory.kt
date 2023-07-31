package com.yudikryn.pokemonapps.helper

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yudikryn.pokemonapps.data.PokemonRepository
import com.yudikryn.pokemonapps.di.Injection
import com.yudikryn.pokemonapps.ui.detailPokemon.DetailPokemonViewModel
import com.yudikryn.pokemonapps.ui.listPokemon.ListPokemonViewModel
import com.yudikryn.pokemonapps.ui.myPokemon.MyPokemonViewModel

class ViewModelFactory private constructor(private val pokemonRepository: PokemonRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyPokemonViewModel::class.java)) {
            return MyPokemonViewModel(pokemonRepository) as T
        }
        if (modelClass.isAssignableFrom(ListPokemonViewModel::class.java)) {
            return ListPokemonViewModel(pokemonRepository) as T
        }
        if (modelClass.isAssignableFrom(DetailPokemonViewModel::class.java)) {
            return DetailPokemonViewModel(pokemonRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}