package com.example.movee.scene.moviedetail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movee.base.BaseAndroidViewModel
import com.example.movee.domain.FetchMovieCreditsUseCase
import com.example.movee.domain.FetchMovieDetailUseCase
import com.example.movee.uimodel.MovieCreditUiModel
import com.example.movee.uimodel.MovieDetailUiModel
import com.example.movee.uimodel.ShowUiModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val fetchMovieDetailUseCase: FetchMovieDetailUseCase,
    private val fetchMovieCreditsUseCase: FetchMovieCreditsUseCase,
    application: Application
) : BaseAndroidViewModel(application) {

    private val _movieDetails = MutableLiveData<MovieDetailUiModel>()
    private val _movieCredits = MutableLiveData<MovieCreditUiModel>()
    val movieDetails: LiveData<MovieDetailUiModel> get() = _movieDetails
    val movieCredits: LiveData<MovieCreditUiModel> get() = _movieCredits

    fun fetchMovieDetails(show: ShowUiModel) {
        if (_movieDetails.value == null) {
            bgScope.launch {
                val movieDetailResult =
                    fetchMovieDetailUseCase.run(FetchMovieDetailUseCase.Params(show.id))

                onUIThread {
                    movieDetailResult.either(::handleFailure, ::postMovieDetail)
                }
            }
        }
    }

    private fun postMovieDetail(movieDetailUiModel: MovieDetailUiModel) {
        _movieDetails.value = movieDetailUiModel
    }

    fun fetchMovieCredits(show: ShowUiModel) {
        if (_movieCredits.value == null) {
            bgScope.launch {
                val movieCreditsResult =
                    fetchMovieCreditsUseCase.run(FetchMovieCreditsUseCase.Params(show.id))

                onUIThread {
                    movieCreditsResult.either(::handleFailure, ::postMovieCredits)
                }
            }
        }
    }

    private fun postMovieCredits(movieCreditUiModel: MovieCreditUiModel) {
        _movieCredits.value = movieCreditUiModel
    }
}
