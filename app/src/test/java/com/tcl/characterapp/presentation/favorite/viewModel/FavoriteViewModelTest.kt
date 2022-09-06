package com.tcl.characterapp.presentation.favorite.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.tcl.characterapp.data.repository.RepositoryImpl
import com.tcl.characterapp.domain.model.CharactersDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    val dispatcher = StandardTestDispatcher()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var favoriteViewModel: FavoriteViewModel

    @Mock
    private lateinit var repository: RepositoryImpl

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
        favoriteViewModel = FavoriteViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get Favorite Characters`() = runBlocking {
        val data = flowOf(listOf(charactersDomain))
        whenever(repository.getAllFavoriteCharacters()).thenReturn(data)
        favoriteViewModel.state.asLiveData().observeForever {
            assertEquals(data, it)
        }
    }

    @Test
    fun deleteCharacter() = runBlocking{
        val data = repository.deleteCharacterFromMyFavoriteList(charactersDomain)
        whenever(data).thenReturn(data)
        favoriteViewModel.state.asLiveData().observeForever {
            assertEquals(data, it)
        }
    }
}