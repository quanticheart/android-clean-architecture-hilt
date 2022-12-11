package com.quanticheart.hiltlist.data

import android.content.Context
import com.quanticheart.core.ApiModule
import com.quanticheart.hiltlist.data.mappers.MovieMapper
import com.quanticheart.hiltlist.domain.models.Movie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

//
// Created by Jonn Alves on 04/12/22.
//
@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {
    @Singleton
    @Provides
    fun getServiceModule(@ApplicationContext context: Context): ApiServices =
        ApiModule.provideRetrofit()
}

@Module
@InstallIn(SingletonComponent::class)
class RepositoryImpl @Inject constructor(
    private val apiServices: ApiServices,
    private val mapper: MovieMapper
) {
    suspend fun getPopularMoviesList(page: Int): Result<List<Movie>> {
        val response = apiServices.getPopularMoviesList(page)
        return if (response.isSuccessful) {
            response.body()?.results?.let {
                Result.success(mapper.mapList(it))
            } ?: kotlin.run {
                Result.failure(Throwable("Error"))
            }
        } else {
            Result.failure(Throwable("Error"))
        }
    }

    suspend fun getMovieDetails(id: Int): Result<Movie> {
        val response = apiServices.getMovieDetails(id)
        return if (response.isSuccessful) {
            response.body()?.let {
                Result.success(mapper.map(it))
            } ?: kotlin.run {
                Result.failure(Throwable("Error"))
            }
        } else {
            Result.failure(Throwable("Error"))
        }
    }
}