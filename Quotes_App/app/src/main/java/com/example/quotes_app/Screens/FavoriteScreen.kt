package com.example.quotes_app.Screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quotes_app.DataManager
import com.example.quotes_app.Models.Quote
import com.example.quotes_app.R

@Composable
fun FavoritesScreen(onClick: (quote: Quote) -> Unit) {
    BackHandler {
        DataManager.backToList()
    }
    val favoriteQuotes = DataManager.favoriteQuotes.value


    Column(
        modifier = Modifier.fillMaxSize()
                .fillMaxWidth()
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF000000),
                        Color(0xFFF2E9E4)
                    )
                )
            )
            .padding(PaddingValues(top = 30.dp)),
    ) {
        Text(
            text = "Favorite Quotes",
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.padding(PaddingValues(start = 50.dp)),
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = FontFamily(Font(R.font.poppins_bold))
        )

        QuoteList(data = favoriteQuotes.toTypedArray(), onClick)
    }
}
