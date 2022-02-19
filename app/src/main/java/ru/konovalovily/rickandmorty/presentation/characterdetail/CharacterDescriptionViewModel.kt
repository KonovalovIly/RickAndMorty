package ru.konovalovily.rickandmorty.presentation.characterdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.konovalovily.rickandmorty.domain.LoadingRepository
import ru.konovalovily.rickandmorty.domain.entity.Character
import ru.konovalovily.rickandmorty.domain.usecases.GetCharacterUseCase

class CharacterDescriptionViewModel(repository: LoadingRepository) : ViewModel() {

    private val _characterInfo = MutableLiveData<Character>()
    val characterInfo: LiveData<Character>
        get() = _characterInfo

    private val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val getCharacterUseCase = GetCharacterUseCase(repository)

    init {
        _loading.value = true
    }

    fun getCharacterDetail(characterId: Int) {
        viewModelScope.launch {
            val result = kotlin.runCatching { getCharacterUseCase.invoke(characterId) }
            result.onSuccess { _characterInfo.value = it }
            result.onFailure { _error.value = it }
        }
    }

    fun endLoading() {
        _loading.value = false
    }

}