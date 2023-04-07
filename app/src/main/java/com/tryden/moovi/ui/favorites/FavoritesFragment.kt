package com.tryden.moovi.ui.favorites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tryden.moovi.R
import com.tryden.moovi.databinding.FragmentFavoritesBinding
import com.tryden.moovi.ui.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private var _binding: FragmentFavoritesBinding? = null
    val binding: FragmentFavoritesBinding get() = _binding!!

    private val viewModel: MoviesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavoritesBinding.bind(view)

        lifecycleScope.launchWhenCreated {
            viewModel.favoriteMovies.collectLatest {
                if (it.isNotEmpty()) {
                    Log.e("FavoritesFragment", "favorites size: ${it.size} " )
                } else {
                    Log.e("FavoritesFragment", "favorite list is empty" )
                }
                binding.countTextView.text = it.size.toString()
            }
        }
    }
}