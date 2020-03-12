package com.example.movee.data.remote.model.movie

import com.example.movee.data.remote.BaseResponseModel
import com.example.movee.internal.util.Image
import com.example.movee.uimodel.MovieDetailUiModel
import com.squareup.moshi.Json
import java.util.Date

data class MovieDetailResponseModel(
    @Json(name = "id") val id: Long,
    @Json(name = "title") val title: String,
    @Json(name = "overview") val overview: String,
    @Json(name = "genres") val genres: List<MovieGenreItemResponseModel>,
    @Json(name = "runtime") val runtime: Int,
    @Json(name = "poster_path") @Image val posterPath: String?,
    @Json(name = "popularity") val popularity: Double,
    @Json(name = "vote_average") val average: Double,
    @Json(name = "release_date") val releaseDate: Date?
) : BaseResponseModel() {

    fun toUiModel() = MovieDetailUiModel(
        id = id,
        title = title,
        overview = overview,
        genres = genres.map { it.toUiModel() },
        posterPath = posterPath,
        popularity = popularity,
        average = average,
        runtime = runtime,
        releaseDate = releaseDate
    )
}