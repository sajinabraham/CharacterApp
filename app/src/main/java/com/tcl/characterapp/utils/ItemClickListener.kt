package com.tcl.characterapp.utils

import com.tcl.characterapp.domain.model.CharactersDomain

class ItemLongClickListener(val longClickListener: (character: CharactersDomain) -> Unit) {
    fun onLongClick(character: CharactersDomain) = longClickListener(character)
}