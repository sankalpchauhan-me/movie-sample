package me.sankalpchauhan.swiggymachinecoding.presentation.listing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import me.sankalpchauhan.swiggymachinecoding.data.model.MoviesRequest
import me.sankalpchauhan.swiggymachinecoding.data.model.MoviesResponse
import me.sankalpchauhan.swiggymachinecoding.domain.model.DataState
import me.sankalpchauhan.swiggymachinecoding.domain.repository.MoviesRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MoviesRepository
): ViewModel() {
    private val _userQuery: MutableStateFlow<String> = MutableStateFlow("")
    private val _uiState: MutableStateFlow<ListingState> = MutableStateFlow(ListingState())
    val uiState: StateFlow<ListingState> = _uiState

    init {
        getMovies(MoviesRequest(_uiState.value.currentPage, _uiState.value.userQuery))
        debounce()
    }

    private fun debounce(){
        viewModelScope.launch {
            _userQuery.debounce(300).flatMapLatest { value->
                repository.getMovies(MoviesRequest(_uiState.value.currentPage, value))
            }.collect{ state->
                handleState(state)
            }
        }
    }

    fun getMovies(moviesRequest: MoviesRequest){
        viewModelScope.launch {
            repository.getMovies(moviesRequest).collect{state->
                handleState(state)
            }
        }
    }

    fun userQuery(string: String){
        _uiState.value = _uiState.value.copy(userQuery = string)
        _userQuery.value = string
    }

    private fun handleState(state: DataState<MoviesResponse>) {
        _uiState.value = when (state) {
            is DataState.Error -> _uiState.value.copy(
                isLoading = false,
                error = state.throwable?.message
            )
            DataState.Loading -> _uiState.value.copy(isLoading = true)
            is DataState.Success -> _uiState.value.copy(
                isLoading = false,
                error = null,
                movies = state.data.movies
            )
        }
    }
}