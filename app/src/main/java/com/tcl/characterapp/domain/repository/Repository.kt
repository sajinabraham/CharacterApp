package com.tcl.characterapp.domain.repository

import androidx.paging.PagingData
import com.tcl.characterapp.data.remote.character.CharacterData
import com.tcl.characterapp.domain.model.CharactersDomain
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getAllCharacters(
        name: String = ""
    ): Flow<PagingData<CharacterData>>


    suspend fun getCharacterDetailById(characterId: Int): CharacterData

    suspend fun getAllFavoriteCharacters(): Flow<List<CharactersDomain>>

    suspend fun insertMyFavoriteList(character: CharactersDomain)

    suspend fun deleteCharacterFromMyFavoriteList(character: CharactersDomain)
}