package com.tryden.moovi.network.service

import com.tryden.moovi.network.response.MovieNowPlayingResponse
import retrofit2.Response
import retrofit2.http.GET

interface MovieService {

    // Now playing
    @GET("movie/now_playing")
    suspend fun getMoviesNowPlaying() : Response<MovieNowPlayingResponse>

}