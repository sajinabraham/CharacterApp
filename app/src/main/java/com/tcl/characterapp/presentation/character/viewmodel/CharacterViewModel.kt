package com.tcl.characterapp.presentation.character.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.tcl.characterapp.R
import com.tcl.characterapp.data.remote.character.toCharactersDomain
import com.tcl.characterapp.domain.model.CharactersDomain
import com.tcl.characterapp.domain.repository.Repository
import com.tcl.characterapp.presentation.character.viewmodel.states.CharacterActivityState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterActivityState())
    val state: StateFlow<CharacterActivityState> get() = _state

    init {
        getAllFavoriteCharacters()

        viewModelScope.launch {

            getListData().collect { it ->
                _state.value = _state.value.copy(
                    characters = it
                )
            }
        }
    }

    suspend fun getListData(): Flow<PagingData<CharactersDomain>> {

        val list = _state.value.favoriteCharacter

        return repository.getAllCharacters(

        ).toCharactersDomain(list)
    }


    fun insertMyFavoriteList(character: CharactersDomain) {
        viewModelScope.launch {
            try {
                repository.insertMyFavoriteList(character)
                //updateToastMessage(app.getString(R.string.toast_message_success))
            } catch (e: Exception) {
                //updateToastMessage(app.getString(R.string.toast_message_error))
            }
        }
        updateToastState()
    }

    fun getAllFavoriteCharacters() {
        viewModelScope.launch {
            repository.getAllFavoriteCharacters().collect {
                _state.value = _state.value.copy(
                    favoriteCharacter = it
                )
            }
        }

    }

    fun deleteCharacterFromMyFavoriteList(character: CharactersDomain) {
        viewModelScope.launch {
            try {
                repository.deleteCharacterFromMyFavoriteList(character)
                //updateToastMessage(app.getString(R.string.toast_message_success))
            } catch (e: Exception) {
                //updateToastMessage(app.getString(R.string.toast_message_error))
            }
        }
        updateToastState()
    }

    private fun updateToastMessage(message: String) {
        _state.value = _state.value.copy(
            toastMessage = message
        )
    }

    private fun updateToastState() {
        _state.value = _state.value.copy(
            showToastMessageEvent = true
        )
    }

    fun doneToastMessage() {

        _state.value = _state.value.copy(
            showToastMessageEvent = false,
            toastMessage = ""
        )
    }

    private fun getFavoriteCharacter(): List<CharactersDomain> {
        return _state.value.favoriteCharacter
    }

    fun isHasAddedCharacter(charactersDomain: CharactersDomain): Boolean {

        val myFavoriteList = this.getFavoriteCharacter()
        var result = false

        myFavoriteList.forEach {
            if (it.id == charactersDomain.id) {
                result = true
            }
        }

        return result
    }

    fun getIsShowToastMessage(): Boolean = _state.value.showToastMessageEvent

    fun getToastMessage(): String = _state.value.toastMessage

}