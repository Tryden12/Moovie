package com.tryden.moovi.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tryden.moovi.network.response.MovieNowPlayingResponse
import kotlinx.coroutines.launch

class MoviesViewModel : ViewModel() {

    private val repository = MoviesRepository()

    private val _moviesNowPlaying = MutableLiveData<MovieNowPlayingResponse?>()
    val moviesNowPlayingLiveData: LiveData<MovieNowPlayingResponse?> = _moviesNowPlaying

    fun refreshNowPlaying() {
        viewModelScope.launch {
            val response = repository.getMoviesNowPlaying()

            _moviesNowPlaying.postValue(response)
        }
    }
}