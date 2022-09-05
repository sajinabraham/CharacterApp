package com.tcl.characterapp.domain.repository

import androidx.paging.PagingData
import com.tcl.characterapp.data.remote.character.CharacterData
import com.tcl.characterapp.domain.model.CharactersDomain
import com.tcl.characterapp.utils.GenderState
import com.tcl.characterapp.utils.StatusState
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path

interface Repository {

    suspend fun getAllCharacters(
        status: StatusState = StatusState.NONE,
        gender: GenderState = GenderState.NONE,
        name: String = ""
    ): Flow<PagingData<CharacterData>>


    suspend fun getCharacterDetailById(characterId: Int): CharacterData

    suspend fun getAllFavoriteCharacters(): Flow<List<CharactersDomain>>

    suspend fun insertMyFavoriteList(character: CharactersDomain)

    suspend fun deleteCharacterFromMyFavoriteList(character: CharactersDomain)
}