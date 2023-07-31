package com.yudikryn.pokemonapps.data.remote.model

import com.google.gson.annotations.SerializedName

data class Types(
    @SerializedName("slot")
    val slot: String,
    @SerializedName("type")
    val type: Type,
)

data class Type(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String,
)