package com.tcl.characterapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tcl.characterapp.data.local.CharacterDao
import com.tcl.characterapp.data.remote.CharacterApi
import com.tcl.characterapp.data.remote.character.CharacterData
import com.tcl.characterapp.domain.model.CharactersDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryImplTest {


    val dispatcher = StandardTestDispatcher()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    lateinit var repositoryImpl: RepositoryImpl

    @Mock
    lateinit var characterApi: CharacterApi

    @Mock
    lateinit var characterDao: CharacterDao

    val characterDummyData = CharacterData(
        id = 1,
        name = "sajin",
        status = "alive",
        image = "",
        species = "human",
        url = ""
    )
    val charactersDomain = CharactersDomain(
        id = 1,
        name = "sajin",
        status = "alive",
        image = "",
        species = "human"
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)
        repositoryImpl = RepositoryImpl(characterApi, characterDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getCharacterDetailById() = runBlocking {

        whenever(characterApi.getCharacter(id = 1)).thenReturn(characterDummyData)
        val result = repositoryImpl.getCharacterDetailById(1)
        assertEquals(characterDummyData, result)
    }

    @Test
    fun `get All Favorite Characters`() = runBlocking {
        val data = flowOf(
            listOf(charactersDomain)
        )
        whenever(characterDao.getAllFavoriteCharacters()).thenReturn(data)
        val result = repositoryImpl.getAllFavoriteCharacters()
        assertEquals(data, result)
    }

    @Test
    fun insertMyFavoriteList() = runBlocking {
    }

    @Test
    fun deleteCharacterFromMyFavoriteList() {
    }
}