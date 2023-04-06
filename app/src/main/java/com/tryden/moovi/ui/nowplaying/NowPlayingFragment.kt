package com.tryden.moovi.ui.nowplaying

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.tryden.moovi.R
import com.tryden.moovi.databinding.FragmentNowPlayingBinding
import com.tryden.moovi.domain.NowPlayingItem
import kotlinx.coroutines.flow.collectLatest

class NowPlayingFragment : Fragment(R.layout.fragment_now_playing) {

    private var _binding: FragmentNowPlayingBinding? = null
    val binding: FragmentNowPlayingBinding get() = _binding!!

    private val viewModel: NowPlayingViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNowPlayingBinding.bind(view)

        val epoxyController = NowPlayingEpoxyController()

        lifecycleScope.launchWhenCreated {
            viewModel.flow.collectLatest { pagingData: PagingData<NowPlayingItem> ->
                epoxyController.submitData(pagingData)
            }
        }

        binding.epoxyRecyclerView.setController(epoxyController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}