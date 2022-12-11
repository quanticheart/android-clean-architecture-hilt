package com.quanticheart.hiltlist.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quanticheart.hiltlist.domain.models.Movie
import com.quanticheart.hiltlist.domain.useCases.ApiRepositoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class ScreenState<out T : Any> {
    data class Success<out T : Any>(val value: T) : ScreenState<T>()
    data class Loading(val value: Boolean) : ScreenState<Nothing>()
    data class Error(val cause: Throwable) : ScreenState<Nothing>()
}

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiRepositoryUseCase: ApiRepositoryUseCase) :
    ViewModel() {

    private val _detailsMovie = MutableSharedFlow<ScreenState<List<Movie>>>()
    val detailsMovie: SharedFlow<ScreenState<List<Movie>>> = _detailsMovie

    fun load(page: Int) {
        viewModelScope.launch {
            _detailsMovie.emit(ScreenState.Loading(true))
            kotlin.runCatching {
                apiRepositoryUseCase.getPopularMoviesList(page)
            }.onSuccess {
                it.getOrElse { null }?.let { result ->
                    _detailsMovie.emit(ScreenState.Success(result))
                }
            }.onFailure {
                _detailsMovie.emit(ScreenState.Error(it))
            }
            _detailsMovie.emit(ScreenState.Loading(false))
        }
    }
}