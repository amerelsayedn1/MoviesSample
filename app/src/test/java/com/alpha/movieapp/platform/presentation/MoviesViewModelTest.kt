package com.alpha.movieapp.platform.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alpha.movieapp.platform.network.MoviesRepo
import com.alpha.movieapp.core.data.network.NetworkOutcome
import com.alpha.movieapp.core.data.MoviesResponse
import com.alpha.movieapp.core.data.network.ErrorResponse
import com.alpha.movieapp.core.data.Movie
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule

class MoviesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private var moviesRepo: MoviesRepo = mockk()

    @ObsoleteCoroutinesApi
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @ObsoleteCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @After
    fun reset() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `get Movies Work Successfully`() = runBlocking {

        val movie = Movie(
                id = 414,
                video = false,
                popularity = 20.574,
                voteCount = 3293,
                title = "Batman Forever",
                releaseDate = "1995-06-16",
                originalLanguage = "en",
                originalTitle = "Batman Forever",
                backdropPath = "/snlu32RmjldF9b068UURJg8sQtn.jpg",
                adult = false,
                posterPath = "/k6EQ2OewzjF8TcPgok9wxpPAgXW.jpg",
                genreIds = listOf(28, 80, 14),
                overview = "",
                voteAverage = 5.3f
        )
        val mockData = MoviesResponse(page = 1, totalPages = 1, totalResults = 1, data = arrayListOf(movie))

        //for suspend functions
        coEvery { moviesRepo.getMovies("batman", 1) } answers {
            NetworkOutcome(isRequestSuccess = true, responseBody = mockData, errorResponse = ErrorResponse())
        }

        val viewModel = MoviesViewModel(moviesRepo)
        viewModel.getMovies("batman", 1).join()


        Assert.assertEquals(1, viewModel.movieSuccessResponse.value?.page)
        Assert.assertEquals(1, viewModel.movieSuccessResponse.value?.totalPages)
        Assert.assertEquals(1, viewModel.movieSuccessResponse.value?.totalResults)
        Assert.assertEquals(1, viewModel.movieSuccessResponse.value?.data?.size)

        Assert.assertEquals(movie.id, viewModel.movieSuccessResponse.value?.data?.get(0)?.id)
        Assert.assertEquals(movie.adult, viewModel.movieSuccessResponse.value?.data?.get(0)?.adult)
        Assert.assertEquals(movie.backdropPath, viewModel.movieSuccessResponse.value?.data?.get(0)?.backdropPath)

    }


    @Test
    fun `get Movies Throws`() = runBlocking {
        coEvery { moviesRepo.getMovies("batman", 1) } answers {
            NetworkOutcome(isRequestSuccess = false, responseBody = null, errorResponse = ErrorResponse(arrayListOf("no data")))
        }

        val viewModel = MoviesViewModel(moviesRepo)
        viewModel.getMovies("batman", 1).join()

        Assert.assertEquals(1, viewModel.movieErrorResponse.value?.errors?.size)

    }

}