package ru.konovalovily.rickandmorty.data.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.konovalovily.rickandmorty.data.CharacterApi
import ru.konovalovily.rickandmorty.domain.entity.Character

class RickAndMortyPagingSource(private val api: CharacterApi) : PagingSource<Int, Character>() {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val pageNumber = params.key ?: 1
        return try {
            val response = api.getCharacters(pageNumber)
            val data = response.results

            var nextPageNumber: Int? = null
            if (response.info.next != null) {
                val uri = Uri.parse(response.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
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