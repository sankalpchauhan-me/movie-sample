package me.sankalpchauhan.swiggymachinecoding.presentation.listing

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class MainViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val repository = FakeRepository()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `on success response`() = runTest{
        val sut = MainViewModel(repository)
        sut.userQuery("hello")
        sut.uiState.test {
            val loading = awaitItem()
            println(loading)
            assertTrue(loading.isLoading)
            val success = awaitItem()
            println(success)
            assertTrue(success.movies.isNullOrEmpty().not())
        }
    }
}