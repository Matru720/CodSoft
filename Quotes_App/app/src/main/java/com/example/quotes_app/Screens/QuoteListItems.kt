package com.example.quotes_app.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FormatQuote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.quotes_app.Models.Quote
import com.example.quotes_app.R

@Composable
fun QuoteListItems(quote: Quote, onClick: (quote: Quote) -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0xFF000000),
                        Color(0xFFF2E9E4)
                    )
                )
            )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier
                .clickable { onClick(quote) }
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(18.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.quotation_mark),
                    colorFilter = ColorFilter.tint(Color.White),
                    alignment = Alignment.TopStart,
                    contentDescription = "Quotes",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.Black)
                        //.rotate(180f)
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = quote.text,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 8.dp)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .background(Color(0xFFEEEEEE))
                            .height(1.dp)
                    )
                    Text(
                        text = quote.author,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}
