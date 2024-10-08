package me.sankalpchauhan.swiggymachinecoding.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.sankalpchauhan.swiggymachinecoding.data.api.NetworkService
import me.sankalpchauhan.swiggymachinecoding.data.model.MoviesRequest
import me.sankalpchauhan.swiggymachinecoding.data.model.MoviesResponse
import me.sankalpchauhan.swiggymachinecoding.domain.model.DataState
import me.sankalpchauhan.swiggymachinecoding.utils.API_KEY
import me.sankalpchauhan.swiggymachinecoding.utils.safeApiCall
import javax.inject.Inject

interface MoviesRepository {
    suspend fun getMovies(moviesRequest: MoviesRequest): Flow<DataState<MoviesResponse>>
}

class DefaultMoviesRepository @Inject constructor(
    private val service: NetworkService
): MoviesRepository{
    override suspend fun getMovies(moviesRequest: MoviesRequest): Flow<DataState<MoviesResponse>> {
        return flow {
            emit(DataState.Loading)
            val response = safeApiCall { service.getMovies(moviesRequest.page.toString(), moviesRequest.query, API_KEY) }
            response.onSuccess { data->
                emit(DataState.Success(data))
            }
            response.onFailure { exp->
                emit(DataState.Error(null, exp))
            }
        }
    }

}