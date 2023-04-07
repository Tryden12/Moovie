package com.tryden.moovi.ui.nowplaying

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.tryden.moovi.R
import com.tryden.moovi.data.database.entity.FavoriteEntity
import com.tryden.moovi.databinding.FragmentNowPlayingBinding
import com.tryden.moovi.domain.NowPlayingItem
import com.tryden.moovi.ui.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NowPlayingFragment : Fragment(R.layout.fragment_now_playing) {

    private var _binding: FragmentNowPlayingBinding? = null
    val binding: FragmentNowPlayingBinding get() = _binding!!

//    private val viewModel: NowPlayingViewModel by viewModels()
    private val viewModel: MoviesViewModel by viewModels()


    val epoxyController = NowPlayingEpoxyController(::onFavoriteSelected)


    @OptIn(ObsoleteCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNowPlayingBinding.bind(view)

        lifecycleScope.launchWhenCreated {
            viewModel.favoriteMovies.collectLatest {
                if (it.isNotEmpty()) {
                    it.forEach { favoriteEntity ->
                        Log.e("NowPlayingFragment", "favorite id: ${favoriteEntity.id} " )
                    }
                } else {
                    Log.e("NowPlayingFragment", "favorite list is empty" )

                }
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.nowPlayingMovies.collectLatest { pagingData: PagingData<NowPlayingItem> ->
                epoxyController.submitData(pagingData)
            }
        }

        binding.epoxyRecyclerView.setController(epoxyController)
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun onFavoriteSelected(favoriteSelected: NowPlayingEpoxyController.FavoriteSelected) {
        Log.e("NowPlayingFragment", "onFavoriteSelected: ${favoriteSelected.id}" )
        if (favoriteSelected.isChecked) {
            saveFavoriteMovieToDatabase(favoriteSelected.id)
        } else {
            deleteFavoriteMovieFromDatabase(favoriteSelected.id)
        }
    }

    private fun saveFavoriteMovieToDatabase(id: String) {
        val favoriteEntity = FavoriteEntity( id = id )
        viewModel.addFavoriteMovie(favoriteEntity)
    }

    private fun deleteFavoriteMovieFromDatabase(id: String) {
        val favoriteEntity = FavoriteEntity( id = id )
        viewModel.deleteFavoriteMovie(favoriteEntity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}