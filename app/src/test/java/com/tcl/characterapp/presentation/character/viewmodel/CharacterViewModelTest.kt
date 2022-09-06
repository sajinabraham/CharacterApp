package com.tcl.characterapp.presentation.character.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.tcl.characterapp.data.remote.character.toCharactersDomain
import com.tcl.characterapp.data.repository.FakeRepository
import com.tcl.characterapp.domain.model.CharactersDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharacterViewModelTest {

    val dispatcher = StandardTestDispatcher()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var characterViewModel: CharacterViewModel
    private lateinit var repository: FakeRepository

    val charactersData = CharactersDomain(
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
        repository = FakeRepository()
        characterViewModel = CharacterViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getState() {
    }

//    @Test
//    fun `get List Data`() = runBlocking {
//        whenever(
//            repository.getAllCharacters().toCharactersDomain(
//                listOf(
//                    CharactersDomain(
//                        id = 1,
//                        name = "sajin",
//                        status = "alive",
//                        image = "",
//                        species = "human"
//                    )
//                )
//            )
//        ).thenReturn(Flow<PagingData<CharactersDomain>>)
//        characterViewModel.getListData()
//
//        assertEquals(CharactersDomain(1,"","","",""), repository.getAllCharacters())
//    }

    @Test
    fun insertMyFavoriteList() {
    }

    @Test
    fun getAllFavoriteCharacters() = runBlocking{
        repository.characterList.add(charactersData)
        characterViewModel.getAllFavoriteCharacters()
        characterViewModel.state.asLiveData().observeForever {
            assertEquals(repository.characterList, it)
        }
    }

    @Test
    fun deleteCharacterFromMyFavoriteList() {
        repository.characterList.add(charactersData)
        characterViewModel.deleteCharacterFromMyFavoriteList(charactersData)
        characterViewModel.state.asLiveData().observeForever {
            assertEquals(repository.characterList, it)
        }
    }
}