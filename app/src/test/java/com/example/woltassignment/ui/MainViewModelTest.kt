package com.example.woltassignment.ui

import com.example.woltassignment.FakeMainRepository
import com.example.woltassignment.TestDispatcher
import com.example.woltassignment.core.ApiResponse
import com.example.woltassignment.domain.model.Restaurant
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainViewModelTest {
    private lateinit var mainViewModel: MainViewModel
    private val fakeMainRepository = FakeMainRepository()

    private val testDispatcher = TestDispatcher(StandardTestDispatcher())
    private val testScope = TestScope(testDispatcher.default)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun init() {
        Dispatchers.setMain(testDispatcher.default)
        mainViewModel = MainViewModel(testDispatcher, fakeMainRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Api returns success, verify restaurants in state`() = testScope.runTest {
        MockBuilder().emitRestaurants()
        mainViewModel.state.value shouldBe MainState(mockRestaurants.toImmutableList())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Api throws exception, shows snackbar`() = testScope.runTest {
        val job = launch {
            val effect = mainViewModel.effect.first()
            effect shouldBe MainViewModel.MainEffect.ShowSnackbar("Unknown")
        }

        MockBuilder().withException()
        advanceUntilIdle()
        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Likes a restaurant, verify state is updated`() = testScope.runTest {
        var currentState = mainViewModel.state.value
        val job = launch {
            mainViewModel.state.collect {
                currentState = it
            }
        }

        MockBuilder().emitRestaurants()
        advanceUntilIdle()
        currentState shouldBe MainState(mockRestaurants.toImmutableList())

        val expectedRestaurants = listOf(
            Restaurant(
                id = "1",
                name = "name1",
                description = "description1",
                url = "url1",
                liked = true
            ), Restaurant(
                id = "2",
                name = "name2",
                description = "description2",
                url = "url2",
                liked = false
            )
        )

        mainViewModel.processAction(MainViewModel.MainAction.OnLikeClicked("1", true))
        advanceUntilIdle()
        currentState shouldBe MainState(expectedRestaurants.toImmutableList())

        MockBuilder().emitRestaurants()
        advanceUntilIdle()
        currentState shouldBe MainState(expectedRestaurants.toImmutableList())

        job.cancel()
    }

    private val mockRestaurants = listOf(
        Restaurant(
            id = "1", name = "name1", description = "description1", url = "url1", liked = false
        ),
        Restaurant(
            id = "2", name = "name2", description = "description2", url = "url2", liked = false
        ),
    )

    inner class MockBuilder {

        suspend fun emitRestaurants(restaurants: List<Restaurant> = mockRestaurants): MockBuilder =
            apply {
                fakeMainRepository.channel.send(ApiResponse.Success(restaurants))
            }

        suspend fun withException(): MockBuilder = apply {
            fakeMainRepository.channel.send(ApiResponse.Fail.Unknown)
        }
    }
}