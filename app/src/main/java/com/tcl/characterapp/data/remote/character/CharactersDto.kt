package com.tcl.characterapp.data.remote.character

import com.tcl.characterapp.data.remote.Info
import com.tcl.characterapp.data.remote.character.CharacterData


data class CharactersDto(
    val info: Info,
    val results: List<CharacterData>
)

