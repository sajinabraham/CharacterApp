package com.tcl.characterapp.presentation.favorite

import com.tcl.characterapp.domain.model.CharactersDomain

data class FavoriteState(
    val characterList: List<CharactersDomain> = emptyList(),
    val isError: Boolean = false
)