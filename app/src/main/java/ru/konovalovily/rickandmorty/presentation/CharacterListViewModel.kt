package ru.konovalovily.rickandmorty.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.konovalovily.rickandmorty.domain.LoadingRepository
import ru.konovalovily.rickandmorty.domain.entity.Character
import ru.konovalovily.rickandmorty.domain.usecases.GetCharacterListUseCase

class CharacterListViewModel(repository: LoadingRepository) : ViewModel() {

    private val _characterList = MutableLiveData<List<Character>>()
    val characterList: LiveData<List<Character>>
        get() = _characterList

    private val page = 1

    private val getCharacterListUseCase = GetCharacterListUseCase(repository)

    fun getCharacters() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                runCatching { getCharacterListUseCase.invoke(page) }
            }
            result.onSuccess {
                _characterList.value = it
                Log.d("VIEW", it.toString())
            }
        }
    }

}