package com.tcl.characterapp.data.remote.character

import androidx.paging.PagingData
import androidx.paging.map
import com.tcl.characterapp.domain.model.CharactersDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

data class CharacterData(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val image: String,
    val url: String,
    val created: String
)


fun Flow<PagingData<CharacterData>>.toCharactersDomain(list: List<CharactersDomain>): Flow<PagingData<CharactersDomain>> {


    return map { pagingData ->
        pagingData.map { characterData ->
            CharactersDomain(
                id = characterData.id,
                name = characterData.name,
                status = characterData.status,
                gender = characterData.gender,
                image = characterData.image,
                species = characterData.species,
                isFavorite = list.contains(characterData.toCharactersDomain())
            )
        }


    }
}


fun CharacterData.toCharactersDomain(): CharactersDomain {
    return CharactersDomain(
        id = id,
        name = name,
        status = status,
        gender = gender,
        image = image,
        species = species
    )

}
