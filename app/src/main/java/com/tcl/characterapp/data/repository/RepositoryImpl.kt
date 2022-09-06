package com.tcl.characterapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tcl.characterapp.data.local.CharacterDao
import com.tcl.characterapp.data.remote.CharacterApi
import com.tcl.characterapp.data.remote.character.CharacterData
import com.tcl.characterapp.domain.model.CharactersDomain
import com.tcl.characterapp.domain.repository.Repository
import com.tcl.characterapp.paging.CharactersPagingDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    val api: CharacterApi,
    private val dao: CharacterDao
) : Repository {


    override suspend fun getAllCharacters(
        name: String
    ): Flow<PagingData<CharacterData>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = {
                CharactersPagingDataSource(
                    api,
                    nameQuery = name
                )
            }
        ).flow
    }


    override suspend fun getCharacterDetailById(characterId: Int): CharacterData {

        return api.getCharacter(characterId)
    }


    override suspend fun getAllFavoriteCharacters(): Flow<List<CharactersDomain>> {
        return dao.getAllFavoriteCharacters()
    }

    override suspend fun insertMyFavoriteList(character: CharactersDomain) {
        dao.insertFavoriteCharacter(character = character)
    }

    override suspend fun deleteCharacterFromMyFavoriteList(character: CharactersDomain) {
        dao.deleteFavoriteCharacter(character)
    }


}