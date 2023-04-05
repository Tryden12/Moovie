package com.tryden.moovi.ui.home

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagedListEpoxyController
import com.squareup.picasso.Picasso
import com.tryden.moovi.R
import com.tryden.moovi.databinding.ModelMovieCardBinding
import com.tryden.moovi.network.response.NowPlayingPageResponse
import com.tryden.moovi.ui.epoxy.ViewBindingKotlinModel
import com.tryden.moovi.util.Constants.IMAGE_BASE_URL

class NowPlayingPagingEpoxyController: PagedListEpoxyController<NowPlayingPageResponse.Result>() {

    override fun buildItemModel(
        currentPosition: Int,
        item: NowPlayingPageResponse.Result?,
    ): EpoxyModel<*> {
        return NowPlayingGridItemEpoxyModel(
            imageUrl = IMAGE_BASE_URL + item!!.backdrop_path,
            title = item.title,
            releaseDate = item.release_date
        ).id(item.id)
    }

    data class NowPlayingGridItemEpoxyModel(
        val imageUrl: String,
        val title: String,
        val releaseDate: String
    ): ViewBindingKotlinModel<ModelMovieCardBinding>(R.layout.model_movie_card) {
        override fun ModelMovieCardBinding.bind() {
            Picasso.get().load(imageUrl).into(imageImageView)
            titleTextView.text = title
            releasedTextView.text = releaseDate
        }
    }
}