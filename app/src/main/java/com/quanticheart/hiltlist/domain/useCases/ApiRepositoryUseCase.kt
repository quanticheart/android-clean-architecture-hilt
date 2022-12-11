package com.quanticheart.hiltlist.domain.useCases

import com.quanticheart.hiltlist.data.RepositoryImpl
import javax.inject.Inject

class ApiRepositoryUseCase @Inject constructor(private val repository: RepositoryImpl) {
    suspend fun getPopularMoviesList(page: Int) = repository.getPopularMoviesList(page)
    suspend fun getMovieDetails(id: Int) = repository.getMovieDetails(id)
}