package com.yudikryn.pokemonapps.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.yudikryn.pokemonapps.data.local.db.LocalDao
import com.yudikryn.pokemonapps.data.local.entity.PokemonEntity
import com.yudikryn.pokemonapps.data.remote.api.ApiService
import com.yudikryn.pokemonapps.data.remote.model.DetailPokemon
import com.yudikryn.pokemonapps.data.remote.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.yudikryn.pokemonapps.data.remote.network.Result

class PokemonRepository private constructor(
    private val apiService: ApiService,
    private val localDao: LocalDao
) {
    fun getListPokemon(startFrom: Int, pageSize: Int): LiveData<Result<List<Pokemon>>> = liveData {
        emit(Result.Loading)
        try {
            withContext(Dispatchers.IO) {
                val mPokemon = MutableLiveData<List<Pokemon>>()
                val pokemon: LiveData<List<Pokemon>> = mPokemon

                val getRemoteData = apiService.getPokemon(startFrom = startFrom, pageSize = pageSize).results

                withContext(Dispatchers.Main) {
                    mPokemon.value = getRemoteData
                }

                val result: LiveData<Result<List<Pokemon>>> = pokemon.map { Result.Success(it) }
                emitSource(result)
            }

        } catch (e: Exception) {
            Log.d("PokemonRepository", "getListPokemon: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getMyPokemon(): LiveData<Result<List<PokemonEntity>>> = liveData {
        emit(Result.Loading)
        try {
            withContext(Dispatchers.IO) {
                val mPokemon = MutableLiveData<List<PokemonEntity>>()
                val pokemon: LiveData<List<PokemonEntity>> = mPokemon

                val getLocalData = localDao.getMyPokemon()

                withContext(Dispatchers.Main) {
                    mPokemon.value = getLocalData
                }

                val result: LiveData<Result<List<PokemonEntity>>> = pokemon.map { Result.Success(it) }
                emitSource(result)
            }

        } catch (e: Exception) {
            Log.d("PokemonRepository", "getMyPokemon: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDetailPokemon(id: Int): LiveData<Result<DetailPokemon>> = liveData {
        emit(Result.Loading)
        try {
            withContext(Dispatchers.IO) {
                val mPokemon = MutableLiveData<DetailPokemon>()
                val pokemon: LiveData<DetailPokemon> = mPokemon

                val getRemoteData = apiService.getDetailPokemon(id = id)

                withContext(Dispatchers.Main) {
                    mPokemon.value = getRemoteData
                }

                val result: LiveData<Result<DetailPokemon>> = pokemon.map { Result.Success(it) }
                emitSource(result)
            }

        } catch (e: Exception) {
            Log.d("PokemonRepository", "getDetailPokemon: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun insertPokemon(pokemonEntity: PokemonEntity): LiveData<Result<Long>> = liveData {
        emit(Result.Loading)
        try {
            withContext(Dispatchers.IO) {
                val mPokemon = MutableLiveData<Long>()
                val pokemon: LiveData<Long> = mPokemon

                val getLocalData = localDao.insertPokemon(pokemonEntity = pokemonEntity)

                withContext(Dispatchers.Main) {
                    mPokemon.value = getLocalData
                }

                val result: LiveData<Result<Long>> = pokemon.map { Result.Success(it) }
                emitSource(result)
            }

        } catch (e: Exception) {
            Log.d("PokemonRepository", "insertPokemon: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun updatePokemon(pokemonEntity: PokemonEntity): LiveData<Result<Int>> = liveData {
        emit(Result.Loading)
        try {
            withContext(Dispatchers.IO) {
                val mPokemon = MutableLiveData<Int>()
                val pokemon: LiveData<Int> = mPokemon

                val getLocalData = localDao.updatePokemon(pokemonEntity = pokemonEntity)

                withContext(Dispatchers.Main) {
                    mPokemon.value = getLocalData
                }

                val result: LiveData<Result<Int>> = pokemon.map { Result.Success(it) }
                emitSource(result)
            }

        } catch (e: Exception) {
            Log.d("PokemonRepository", "updatePokemon: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    fun deletePokemon(id: Int): LiveData<Result<Int>> = liveData {
        emit(Result.Loading)
        try {
            withContext(Dispatchers.IO) {
                val mPokemon = MutableLiveData<Int>()
                val pokemon: LiveData<Int> = mPokemon

                val getLocalData = localDao.deletePokemonById(id = id)
                withContext(Dispatchers.Main) {
                    mPokemon.value = getLocalData
                }

                val result: LiveData<Result<Int>> = pokemon.map { Result.Success(it) }
                emitSource(result)
            }

        } catch (e: Exception) {
            Log.d("PokemonRepository", "deletePokemonanjay: ${e} ")
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: PokemonRepository? = null
        fun getInstance(
            apiService: ApiService,
            localDao: LocalDao
        ): PokemonRepository =
            instance ?: synchronized(this) {
                instance ?: PokemonRepository(apiService, localDao)
            }.also { instance = it }
    }
}