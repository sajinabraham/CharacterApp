package com.tcl.characterapp.presentation.character.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.tcl.characterapp.data.remote.character.CharacterData
import com.tcl.characterapp.data.repository.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class CharacterDetailViewModelTest {

    val dispatcher = StandardTestDispatcher()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var characterDetailViewModel: CharacterDetailViewModel

    @Mock
    private lateinit var repository: RepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(dispatcher)
        characterDetailViewModel = CharacterDetailViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getCharacterIDFromFragmentList() = runBlocking {
        val data =
            CharacterData(id = 1, name = "sajin", status = "alive", species = "human", "", "")
        whenever(repository.getCharacterDetailById(characterId = 1)).thenReturn(data)
        characterDetailViewModel.getCharacterIDFromFragmentList()
        characterDetailViewModel.state.asLiveData().observeForever {
            assertEquals(data, it)
        }
    }
}