package com.tcl.characterapp.presentation.character.viewmodel.states

import com.tcl.characterapp.data.remote.character.CharacterData

data class CharacterDetailState(
    val character: CharacterData? = null,
    val characterIdFromCharacterListFragment: Int = 1,
    val navigateArgLocationId: Int? = null,
)