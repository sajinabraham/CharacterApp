package com.tcl.characterapp.presentation.character.viewmodel.states

import androidx.paging.PagingData
import com.tcl.characterapp.domain.model.CharactersDomain

data class CharacterActivityState(
    val characters: PagingData<CharactersDomain>? = PagingData.empty(),
    val favoriteCharacter: List<CharactersDomain> = emptyList(),
    val showToastMessageEvent: Boolean = false,
    val toastMessage: String = "Successfully Added to Favorite"

)

enum class ListType() {
    GridLayout()
}


