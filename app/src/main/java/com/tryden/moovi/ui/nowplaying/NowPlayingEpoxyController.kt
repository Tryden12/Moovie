package com.tryden.moovi.ui.nowplaying

import android.annotation.SuppressLint
import android.util.Log
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.squareup.picasso.Picasso
import com.tryden.moovi.R
import com.tryden.moovi.application.MooviApplication
import com.tryden.moovi.databinding.ModelHeaderSectionTitleBinding
import com.tryden.moovi.databinding.ModelMovieCardBinding
import com.tryden.moovi.domain.NowPlayingItem
import com.tryden.moovi.ui.epoxy.ViewBindingKotlinModel
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class NowPlayingEpoxyController(
    private val onFavoriteSelected: (FavoriteSelected) -> Unit
): PagingDataEpoxyController<NowPlayingItem>() {

    override fun buildItemModel(currentPosition: Int, item: NowPlayingItem?): EpoxyModel<*> {
        return NowPlayingGridItemEpoxyModel(
                    item = item,
                    onFavoriteSelected
                ).id("nowplaying-${item!!.id}")
    }

    data class NowPlayingGridItemEpoxyModel(
        val item: NowPlayingItem?,
        val onFavoriteSelected: (FavoriteSelected) -> Unit
    ): ViewBindingKotlinModel<ModelMovieCardBinding>(R.layout.model_movie_card) {
        @SuppressLint("UseCompatLoadingForDrawables")
        override fun ModelMovieCardBinding.bind() {
            Picasso.get().load(item!!.imageUrl).into(imageImageView)
            titleTextView.text = item.title
            releasedTextView.text = item.releaseDate

            favoriteCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
                onFavoriteSelected(
                    FavoriteSelected(item.id, isChecked)
                )
            }
        }
    }

    data class NowPlayingSectionTitleEpoxyModel(
        val title: String
    ): ViewBindingKotlinModel<ModelHeaderSectionTitleBinding>(R.layout.model_header_section_title) {
        override fun ModelHeaderSectionTitleBinding.bind() {
            titleTextView.text = title
        }
    }

    data class FavoriteSelected(
        val id: String,
        val isChecked: Boolean
    )

}