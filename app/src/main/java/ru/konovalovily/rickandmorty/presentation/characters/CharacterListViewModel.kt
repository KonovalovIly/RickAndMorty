package ru.konovalovily.rickandmorty.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.konovalovily.rickandmorty.domain.LoadingRepository
import ru.konovalovily.rickandmorty.domain.entity.Character
import ru.konovalovily.rickandmorty.domain.usecases.GetCharacterListUseCase

class CharacterListViewModel(repository: LoadingRepository) : ViewModel() {

    private lateinit var _characterList: Flow<PagingData<Character>>
    val characterList: Flow<PagingData<Character>>
        get() = _characterList

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    private val getCharacterListUseCase = GetCharacterListUseCase(repository)

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            val result =
                kotlin.runCatching { getCharacterListUseCase.invoke().cachedIn(viewModelScope) }
            result.onSuccess { _characterList = it }
            result.onFailure { _error.value = it }
        }
    }

}