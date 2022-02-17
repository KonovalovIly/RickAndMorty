package ru.konovalovily.rickandmorty.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
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

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean>
        get() = _progress

    private val _loadError = MutableLiveData<Boolean>()
    val loadError: LiveData<Boolean>
        get() = _loadError

    private val getCharacterListUseCase = GetCharacterListUseCase(repository)

    init {
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch {
            val result = kotlin.runCatching { getCharacterListUseCase.invoke() }
            result.onSuccess { _characterList = it }
            result.onFailure { _error.value = it }
        }
    }

    fun startLoad(){
        _progress.value = true
    }

    fun endLoad(){
        _progress.value = false
    }

    fun loadError(){
        _loadError.value = true
    }

    fun endError(){
        _loadError.value = false
    }

}