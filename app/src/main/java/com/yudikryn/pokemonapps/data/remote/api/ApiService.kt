package com.yudikryn.pokemonapps.data.remote.api

import com.yudikryn.pokemonapps.data.remote.model.DetailPokemon
import com.yudikryn.pokemonapps.data.remote.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemon(
        @Query("offset") startFrom: Int = 0,
        @Query("limit") pageSize: Int = 10
    ): PokemonResponse

    @GET("pokemon/{id}/")
    suspend fun getDetailPokemon(
        @Path("id") id: Int
    ): DetailPokemon
}