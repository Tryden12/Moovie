package com.tryden.moovi.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.tryden.moovi.R
import com.tryden.moovi.application.MooviApplication
import com.tryden.moovi.database.AppDatabase
import com.tryden.moovi.databinding.FragmentHomeBinding
import com.tryden.moovi.ui.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    val binding: FragmentHomeBinding get() = _binding!!

    val viewModel: MoviesViewModel by viewModels()

    private val epoxyController = NowPlayingPagingEpoxyController()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

//        viewModel.refreshNowPlaying()
//        viewModel.moviesNowPlayingLiveData.observe(viewLifecycleOwner) { response ->
//            if (response == null) {
//                Toast.makeText(
//                    context,
//                    "Unsuccessful network call",
//                    Toast.LENGTH_SHORT
//                ).show()
//                return@observe
//            }
//
//            Log.e("MainActivity", "response results size ${response.results.size}" )
//        }

        viewModel.nowPlayingPagedListLiveData.observe(viewLifecycleOwner) { pagedList ->
            epoxyController.submitList(pagedList)
        }

        binding.epoxyRecyclerView.setController(epoxyController)
    }

}