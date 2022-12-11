package com.quanticheart.hiltlist.data.mappers

import com.quanticheart.hiltlist.data.responses.MovieDetailsResponse
import com.quanticheart.hiltlist.data.responses.MoviesListResponse
import com.quanticheart.hiltlist.domain.models.Movie
import javax.inject.Inject

//
// Created by Jonn Alves on 04/12/22.
//

class MovieMapper @Inject constructor() {
    fun mapList(results: List<MoviesListResponse.Result>): List<Movie> {
        return results.map { raw ->
            Movie().apply {
                title = raw.title
            }
        }
    }

    fun map(result: MovieDetailsResponse): Movie {
        return Movie().apply {
            title = result.title
        }
    }
}