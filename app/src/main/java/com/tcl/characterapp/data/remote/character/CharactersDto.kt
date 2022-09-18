package com.tcl.characterapp.data.remote.character

import com.tcl.characterapp.data.remote.Info


data class CharactersDto(
    val info: Info,
    val results: List<CharacterData>
)

