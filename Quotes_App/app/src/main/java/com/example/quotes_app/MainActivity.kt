package com.example.quotes_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.quotes_app.Screens.FavoritesScreen
import com.example.quotes_app.Screens.QuoteDetails
import com.example.quotes_app.Screens.QuoteListScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }

    override fun onStop() {
        super.onStop()
        // Save the current quote index when the app is stopped
        DataManager.saveQuoteIndex(this)
    }
}

@Composable
fun App() {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        delay(1000) // Simulate loading delay
        DataManager.loadAssetsFromFile(context)
    }

    if (DataManager.isDataLoaded.value) {
        when (DataManager.currentPage.value) {
            Pages.Listings -> QuoteListScreen(data = DataManager.currentQuotes.value) {
                DataManager.switchPages(it)
            }
            Pages.Details -> DataManager.currentQuote?.let {
                QuoteDetails(quote = it)
            }
            Pages.Favorites -> FavoritesScreen {
                DataManager.switchPages(it)
            }
        }
    } else {
        IndeterminateCircularIndicator()
    }
}

@Composable
fun IndeterminateCircularIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = MaterialTheme.colorScheme.secondary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

enum class Pages {
    Listings,
    Details,
    Favorites
}
