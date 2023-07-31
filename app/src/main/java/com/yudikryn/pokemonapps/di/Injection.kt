package com.yudikryn.pokemonapps.di

import android.content.Context
import com.yudikryn.pokemonapps.data.PokemonRepository
import com.yudikryn.pokemonapps.data.local.db.LocalDb
import com.yudikryn.pokemonapps.data.remote.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): PokemonRepository {
        val apiService = ApiConfig.getApiService()
        val localDao = LocalDb.getInstance(context).localDao()
        return PokemonRepository.getInstance(apiService, localDao)
    }
}