package me.sankalpchauhan.swiggymachinecoding.presentation.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import dagger.hilt.android.AndroidEntryPoint
import me.sankalpchauhan.swiggymachinecoding.R
import me.sankalpchauhan.swiggymachinecoding.data.model.MoviesResponse
import me.sankalpchauhan.swiggymachinecoding.presentation.base.ui.theme.SwiggyMachineCodingTheme
import me.sankalpchauhan.swiggymachinecoding.presentation.listing.ComposeImageView

@AndroidEntryPoint
class DetailActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: Create a compat function
        val movie = intent.getParcelableExtra<MoviesResponse.Movies>(ARG_KEY)?: error("Activity arguments must be supplied")
        setContent {
            SwiggyMachineCodingTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Column {
                            Spacer(modifier = Modifier.height(40.dp))
                            Text(text = stringResource(R.string.my_movie_app))
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()){
                        Column {
                            ComposeImageView(modifier = Modifier.fillMaxWidth()
                                .height(120.dp),movie.poster)
                            Text(fontSize = 20.sp,text = movie.title?: stringResource(id = R.string.n_a), fontWeight = FontWeight.Bold)
                            HorizontalDivider()
                            movie.type?.let {
                                LineItem(it, stringResource(R.string.type))
                                HorizontalDivider()
                            }
                            movie.year?.let {
                                LineItem(it, stringResource(R.string.year))
                                HorizontalDivider()
                            }
                            movie.imdbID?.let {
                                LineItem(it, stringResource(R.string.imdb_id))
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun LineItem(string: String, title: String) {
        Column {
            Text(modifier = Modifier.fillMaxWidth(),text = title)
            Text(modifier = Modifier.fillMaxWidth(), text = string)
        }
    }

    companion object{
        const val ARG_KEY = "ARG_KEY"
        fun launchDetailActivity(context: Context,movies: MoviesResponse.Movies){
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(ARG_KEY,movies)
            }
            context.startActivity(intent)
        }
    }
}