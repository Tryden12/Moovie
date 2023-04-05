package com.tryden.moovi.network.service

import com.tryden.moovi.network.response.NowPlayingPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    // Now playing
    @GET("movie/now_playing")
    suspend fun getMoviesNowPlaying() : Response<NowPlayingPageResponse>

    // Now playing paging
    @GET("movie/now_playing")
    suspend fun getNowPlayingMoviesPage(
        @Query("page") pageIndex: Int
    ): Response<NowPlayingPageResponse>
}