package com.tryden.moovi.network.service

import com.tryden.moovi.network.response.MovieNowPlayingResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {

    // Now playing todo: remove api key
    @GET("movie/now_playing?api_key=7bfe007798875393b05c5aa1ba26323e")
    suspend fun getMoviesNowPlaying() : Response<MovieNowPlayingResponse>
}