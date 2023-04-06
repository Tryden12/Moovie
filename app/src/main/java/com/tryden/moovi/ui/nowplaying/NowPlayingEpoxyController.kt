package com.tryden.moovi.ui.nowplaying

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.squareup.picasso.Picasso
import com.tryden.moovi.R
import com.tryden.moovi.databinding.ModelMovieCardBinding
import com.tryden.moovi.domain.NowPlayingItem
import com.tryden.moovi.ui.epoxy.ViewBindingKotlinModel
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class NowPlayingEpoxyController(

): PagingDataEpoxyController<NowPlayingItem>() {

    override fun buildItemModel(currentPosition: Int, item: NowPlayingItem?): EpoxyModel<*> {
        return NowPlayingGridItemEpoxyModel(
            item = item!!,
            onItemSelected = { itemId ->
                // todo
            }
        ).id("nowplaying-${item.id}")
    }

    data class NowPlayingGridItemEpoxyModel(
        val item: NowPlayingItem,
        val onItemSelected: (String) -> Unit
    ): ViewBindingKotlinModel<ModelMovieCardBinding>(R.layout.model_movie_card) {
        override fun ModelMovieCardBinding.bind() {
            Picasso.get().load(item.imageUrl).into(imageImageView)
            titleTextView.text = item.title
            releasedTextView.text = item.releaseDate

            root.setOnClickListener { onItemSelected(item.id) }
        }
    }

}