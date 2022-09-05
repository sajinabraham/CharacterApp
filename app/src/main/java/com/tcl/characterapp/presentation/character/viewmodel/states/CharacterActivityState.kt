package com.tcl.characterapp.presentation.character.viewmodel.states

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.tcl.characterapp.domain.model.CharactersDomain
import com.tcl.characterapp.utils.GenderState
import com.tcl.characterapp.utils.StatusState

data class CharacterActivityState(
    val characters: PagingData<CharactersDomain>? = PagingData.empty(),
    val favoriteCharacter: List<CharactersDomain> = emptyList(),
    val showToastMessageEvent: Boolean = false,
    val toastMessage: String = "Successfully Added to Favorite"

)

enum class ListType() {
    GridLayout()
}


