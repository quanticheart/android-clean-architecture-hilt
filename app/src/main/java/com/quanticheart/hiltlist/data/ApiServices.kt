package com.quanticheart.hiltlist.data

import com.quanticheart.hiltlist.data.responses.MovieDetailsResponse
import com.quanticheart.hiltlist.data.responses.MoviesListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @GET("movie/popular")
    suspend fun getPopularMoviesList(@Query("page") page: Int): Response<MoviesListResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Int): Response<MovieDetailsResponse>
}