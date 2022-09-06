package com.tcl.characterapp.data.remote

import com.tcl.characterapp.data.remote.character.CharacterData
import com.tcl.characterapp.data.remote.character.CharactersDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("name") name: String="",
        @Query("page") page: Int? = null
    ): CharactersDto


    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterData


}