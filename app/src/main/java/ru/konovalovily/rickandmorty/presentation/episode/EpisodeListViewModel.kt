package ru.konovalovily.rickandmorty.presentation.episode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.konovalovily.rickandmorty.domain.LoadingRepository
import ru.konovalovily.rickandmorty.domain.entity.Episode
import ru.konovalovily.rickandmorty.domain.usecases.GetEpisodesListUseCase

class EpisodeListViewModel(repository: LoadingRepository) : ViewModel() {

    private lateinit var _episodeList: Flow<PagingData<Episode>>
    val episodeList: Flow<PagingData<Episode>>
        get() = _episodeList

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    private val getEpisodeListUseCase = GetEpisodesListUseCase(repository)

    private fun getEpisodes(list: List<String>?) {
        viewModelScope.launch {
            val result =
                kotlin.runCatching { getEpisodeListUseCase.invoke().cachedIn(viewModelScope) }
            result.onSuccess {
                _episodeList = if (list != null) {
                    it.map { data ->
                        data.filter { episode ->
                            list.contains(episode.url)
                        }
                    }
                } else it
            }
            result.onFailure { _error.value = it }
        }
    }

    fun filterEpisodesList(list: List<String>?) {
        getEpisodes(list)
    }

}