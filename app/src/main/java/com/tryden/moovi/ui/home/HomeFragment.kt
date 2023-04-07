package com.tryden.moovi.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.tryden.moovi.R
import com.tryden.moovi.data.database.entity.FavoriteEntity
import com.tryden.moovi.databinding.FragmentHomeBinding
import com.tryden.moovi.domain.NowPlayingItem
import com.tryden.moovi.ui.home.NowPlayingEpoxyController.FavoriteSelected
import com.tryden.moovi.ui.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    val binding: FragmentHomeBinding get() = _binding!!

    private val viewModel: MoviesViewModel by viewModels()

    val epoxyController = NowPlayingEpoxyController(::onFavoriteSelected)


    @OptIn(ObsoleteCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

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
    private fun onFavoriteSelected(favoriteSelected: FavoriteSelected) {
        Log.e("NowPlayingFragment", "onFavoriteSelected: ${favoriteSelected.item.id}" )
        if (favoriteSelected.isChecked) {
            saveFavoriteMovieToDatabase(favoriteSelected)
        } else {
            deleteFavoriteMovieFromDatabase(favoriteSelected)
        }
    }

    private fun saveFavoriteMovieToDatabase(favorite: FavoriteSelected) {
        val favoriteEntity = FavoriteEntity(
            id = favorite.item.id,
            title = favorite.item.title,
            imageUrl = favorite.item.imageUrl,
            releaseDate = favorite.item.releaseDate
        )
        viewModel.addFavoriteMovie(favoriteEntity)
    }

    private fun deleteFavoriteMovieFromDatabase(favorite: FavoriteSelected) {
        val favoriteEntity = FavoriteEntity(
            id = favorite.item.id,
            title = favorite.item.title,
            imageUrl = favorite.item.imageUrl,
            releaseDate = favorite.item.releaseDate
        )
        viewModel.deleteFavoriteMovie(favoriteEntity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}