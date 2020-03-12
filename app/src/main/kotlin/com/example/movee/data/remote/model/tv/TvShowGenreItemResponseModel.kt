package com.example.movee.data.remote.model.tv

import com.example.movee.data.local.model.TvShowGenreLocalModel
import com.example.movee.data.remote.BaseResponseModel
import com.example.movee.uimodel.TvShowGenreUiModel
import com.squareup.moshi.Json

data class TvShowGenreItemResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String
) : BaseResponseModel() {

    fun toCacheModel() = TvShowGenreLocalModel(id = id, name = name)

    fun toUiModel() = TvShowGenreUiModel(id = id, name = name)
}
