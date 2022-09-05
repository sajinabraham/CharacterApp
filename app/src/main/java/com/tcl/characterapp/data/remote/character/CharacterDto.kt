package com.tcl.characterapp.data.remote.character

import com.tcl.characterapp.domain.model.CharacterDomain

data class CharacterDto(
    val result: CharacterData
)


fun CharacterDto.toCharacter(): CharacterDomain {
    return CharacterDomain(
        result = result
    )
}