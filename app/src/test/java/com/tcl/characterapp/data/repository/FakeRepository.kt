package com.tcl.characterapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tcl.characterapp.data.remote.CharacterApi
import com.tcl.characterapp.data.remote.character.CharacterData
import com.tcl.characterapp.domain.model.CharactersDomain
import com.tcl.characterapp.domain.repository.Repository
import com.tcl.characterapp.paging.CharactersPagingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRepository : Repository {

    val characterList = mutableListOf<CharactersDomain>()
    var api :  CharacterApi? = null

    override suspend fun getAllCharacters(name: String): Flow<PagingData<CharacterData>> {
        return Pager(config = PagingConfig(pageSize = 25), pagingSourceFactory = {
            CharactersPagingDataSource(
                characterApi = api!!,
                nameQuery = name
            )
        }
        ).flow
    }

    override suspend fun getCharacterDetailById(characterId: Int): CharacterData =
        CharacterData(id = 1, name = "sajin", status = "alive", species = "human", "", "")

    override suspend fun getAllFavoriteCharacters(): Flow<List<CharactersDomain>>  = flowOf(characterList)

    override suspend fun insertMyFavoriteList(character: CharactersDomain) {
        characterList.add(character)
    }

    override suspend fun deleteCharacterFromMyFavoriteList(character: CharactersDomain) {
        characterList.remove(character)
    }
}