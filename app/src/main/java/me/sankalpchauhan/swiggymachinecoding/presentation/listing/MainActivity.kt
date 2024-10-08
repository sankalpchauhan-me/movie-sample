package me.sankalpchauhan.swiggymachinecoding.presentation.listing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import dagger.hilt.android.AndroidEntryPoint
import me.sankalpchauhan.swiggymachinecoding.R
import me.sankalpchauhan.swiggymachinecoding.data.model.MoviesResponse
import me.sankalpchauhan.swiggymachinecoding.presentation.base.ui.theme.SwiggyMachineCodingTheme
import me.sankalpchauhan.swiggymachinecoding.presentation.detail.DetailActivity

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val pageState by mainViewModel.uiState.collectAsState()
            var userQuery by remember {
                mutableStateOf("")
            }
            SwiggyMachineCodingTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Column {
                            Spacer(modifier = Modifier.height(40.dp))
                            Text(text = stringResource(R.string.my_movie_app))
                            TextField(modifier = Modifier.fillMaxWidth(),value = userQuery, onValueChange = { query->
                                userQuery = query
                                mainViewModel.userQuery(userQuery)
                            })
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(), contentAlignment = Alignment.Center) {
                        if(pageState.isLoading){
                            CircularProgressIndicator()
                        }
                        else if(pageState.userQuery.isEmpty()){
                            Text(text = "Use above text box to start searching")
                        }
                        else if(pageState.error!=null || pageState.movies==null){
                            Text(text = pageState.error?: stringResource(R.string.something_went_wrong))
                        }
                        else{
                            pageState.movies?.let { movies->
                                LazyColumn(modifier = Modifier.fillMaxSize()) {
                                    items(movies.size, key = {index->movies[index].imdbID}){ position->
                                        val movie = movies[position]
                                        RowItem(movie = movie,url = movie.poster, movieName = movie.title, onClick = {movie->
                                            DetailActivity.launchDetailActivity(context, movie)
                                        })
                                        HorizontalDivider()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RowItem(
    movie: MoviesResponse.Movies,
    url: String?,
    movieName: String?,
    onClick: (MoviesResponse.Movies) -> Unit
){
    Row(modifier = Modifier
        .fillMaxSize()
        .clickable {
            onClick.invoke(movie)
        }, verticalAlignment = Alignment.CenterVertically) {
        ComposeImageView(modifier = Modifier.size(80.dp),url)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = movieName ?: stringResource(R.string.n_a))
    }
}

@Composable
fun ComposeImageView(modifier: Modifier = Modifier,url: String?) {
    SubcomposeAsyncImage(modifier = modifier,
        model = url,
        contentScale = ContentScale.FillBounds,
        contentDescription = null,
        loading = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize())
            }
        },
        error = {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SwiggyMachineCodingTheme {
        Greeting("Android")
    }
}