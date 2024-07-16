package com.example.quotes_app.Screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.quotes_app.DataManager
import com.example.quotes_app.Models.Quote
import com.example.quotes_app.R
import kotlin.text.Typography.quote

@Composable
fun QuoteListScreen(data: Array<Quote>, onClick: (quote: Quote) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF000000),
                            Color(0xFFF2E9E4)
                        )
                    )
                )
                .padding(PaddingValues(top = 30.dp))
                .padding(PaddingValues(start = 20.dp)),
        ) {
            Text(
                text = "Quote App",
                textAlign = TextAlign.Center,
                color = Color.White,

                style = MaterialTheme.typography.headlineLarge,
                fontFamily = FontFamily(Font(R.font.poppins_bold))
            )
            FilledIconButton(
                modifier = Modifier.padding(PaddingValues(start = 90.dp)),
                onClick = {
                    DataManager.switchToFavorites()
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.favorite),
                    colorFilter = ColorFilter.tint(Color.Black),
                    //modifier = Modifier.padding(PaddingValues(start = 10.dp)),
                    contentDescription = "Share",
                    modifier = Modifier.background(Color.White)
                )
            }
            FilledIconButton(
                modifier = Modifier.padding(PaddingValues(start = 10.dp)),
                onClick = {
                    DataManager.resetQuotes()
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.reloading),
                    colorFilter = ColorFilter.tint(Color.Black),
                    contentDescription = "Share",
                    modifier = Modifier.background(Color.White)
                )
            }
        }

        QuoteList(data = data, onClick)

    }
}
