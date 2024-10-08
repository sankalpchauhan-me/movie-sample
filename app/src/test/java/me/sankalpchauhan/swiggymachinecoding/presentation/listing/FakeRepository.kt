package me.sankalpchauhan.swiggymachinecoding.presentation.listing

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import me.sankalpchauhan.swiggymachinecoding.data.model.MoviesRequest
import me.sankalpchauhan.swiggymachinecoding.data.model.MoviesResponse
import me.sankalpchauhan.swiggymachinecoding.domain.model.DataState
import me.sankalpchauhan.swiggymachinecoding.domain.repository.MoviesRepository

class FakeRepository: MoviesRepository {
    val localjson = """
    {
  "Search": [
    {
      "Title": "Deadpool",
      "Year": "2016",
      "imdbID": "tt1431045",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BNzY3ZWU5NGQtOTViNC00ZWVmLTliNjAtNzViNzlkZWQ4YzQ4XkEyXkFqcGc@._V1_SX300.jpg"
    },
    {
      "Title": "Deadpool 2",
      "Year": "2018",
      "imdbID": "tt5463162",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BNGY3N2ZhYmMtYTlmYi00ZWIzLWJiZWMtMjgxMjljYTk3MDAwXkEyXkFqcGc@._V1_SX300.jpg"
    },
    {
      "Title": "Deadpool & Wolverine",
      "Year": "2024",
      "imdbID": "tt6263850",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BZTk5ODY0MmQtMzA3Ni00NGY1LThiYzItZThiNjFiNDM4MTM3XkEyXkFqcGc@._V1_SX300.jpg"
    },
    {
      "Title": "Deadpool: No Good Deed",
      "Year": "2017",
      "imdbID": "tt6612630",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BODAxNDFhNGItMzdiMC00NDM1LWExYWUtZjNiMGIzYTc0MTM5XkEyXkFqcGdeQXVyMjYzMjA3NzI@._V1_SX300.jpg"
    },
    {
      "Title": "Deadpool",
      "Year": "2013",
      "imdbID": "tt2269724",
      "Type": "game",
      "Poster": "https://m.media-amazon.com/images/M/MV5BNjQ2OGI1ZjQtZWE5Yy00ZDQ3LThiZWYtNjU0OGUyOTU3NjZmXkEyXkFqcGdeQXVyNjU1OTg4OTM@._V1_SX300.jpg"
    },
    {
      "Title": "Gettin' Wet on Wet with Deadpool 2",
      "Year": "2017",
      "imdbID": "tt7636826",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BMjZiMmYwNWQtNzUzOS00MTVmLTg2NDQtZmY3MjNjYzUzZTJmXkEyXkFqcGdeQXVyNTE1NjY5Mg@@._V1_SX300.jpg"
    },
    {
      "Title": "Once Upon a Deadpool",
      "Year": "2018",
      "imdbID": "tt31050020",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BYWZmMGVkYzctZjI4OC00ZjVkLTk5M2YtYWI2MDE3MmRmZjA0XkEyXkFqcGc@._V1_SX300.jpg"
    },
    {
      "Title": "Deadpool's Maximum Reactions: Korg and Deadpool",
      "Year": "2021",
      "imdbID": "tt15041304",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BZjY2NjlhYzEtODk1Ni00MWI1LTk4MDctY2RjOWU3YWVkZmYyXkEyXkFqcGdeQXVyMTEyNzgwMDUw._V1_SX300.jpg"
    },
    {
      "Title": "Deadpool",
      "Year": "2013",
      "imdbID": "tt2622240",
      "Type": "series",
      "Poster": "https://m.media-amazon.com/images/M/MV5BOWNhMGFjZjAtMDQ0Yi00MjMxLTkwYjgtMjc4MzE3NDQ2NzExXkEyXkFqcGdeQXVyNTA0OTU0OTQ@._V1_SX300.jpg"
    },
    {
      "Title": "Deadpool: The Musical",
      "Year": "2017",
      "imdbID": "tt6693444",
      "Type": "movie",
      "Poster": "https://m.media-amazon.com/images/M/MV5BYzEyOTU1MDYtZjg3ZS00OWE1LWIwYTMtN2E4OTA1MzE5MmY3XkEyXkFqcGdeQXVyMTkyNzc0MA@@._V1_SX300.jpg"
    }
  ],
  "totalResults": "52",
  "Response": "True"
}""".trimIndent()

    override suspend fun getMovies(moviesRequest: MoviesRequest): Flow<DataState<MoviesResponse>> {
        return flow {
            emit(DataState.Loading)
            emit(DataState.Success(Json.decodeFromString(localjson)))
        }
    }
}