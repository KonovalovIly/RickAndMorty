package ru.konovalovily.rickandmorty.data.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.konovalovily.rickandmorty.data.CharacterApi
import ru.konovalovily.rickandmorty.domain.entity.Constants
import ru.konovalovily.rickandmorty.domain.entity.Episode

class EpisodePagingSource(private val api: CharacterApi) :
    PagingSource<Int, Episode>() {

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        val pageNumber = params.key ?: Constants.START_PAGE
        return try {
            val response = api.getEpisodes(pageNumber)
            val data = response.results
            var nextPageNumber: Int? = null
            if (response.info.next != null) {
                val uri = Uri.parse(response.info.next)
                val nextPageQuery = uri.getQueryParameter(Constants.PAGE_RESPONSE)
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}