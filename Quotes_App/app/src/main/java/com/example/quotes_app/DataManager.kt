package com.example.quotes_app

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.example.quotes_app.Models.Quote
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.nio.charset.Charset

object DataManager {
    private val allQuotes = mutableListOf<Quote>()
    val currentQuotes = mutableStateOf(arrayOf<Quote>())
    val isDataLoaded = mutableStateOf(false)
    val currentPage = mutableStateOf(Pages.Listings)
    var currentQuote: Quote? = null
    private var quoteIndex = 0
    private val quotesPerPage = 10
    private const val PREFS_NAME = "QuotesAppPrefs"
    private const val PREF_QUOTE_INDEX = "quoteIndex"
    private const val PREF_FAVORITES = "favorites"

    val favoriteQuotes = mutableStateOf(listOf<Quote>())

    fun loadAssetsFromFile(context: Context) {
        val inputStream = context.assets.open("quotes.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val json = String(buffer, Charset.forName("UTF-8"))
        val listType = object : TypeToken<Array<Quote>>() {}.type
        val quotes: Array<Quote> = Gson().fromJson(json, listType)
        allQuotes.addAll(quotes)

        // Load quoteIndex and favorites from SharedPreferences
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        quoteIndex = sharedPreferences.getInt(PREF_QUOTE_INDEX, 0)
        val favoritesJson = sharedPreferences.getString(PREF_FAVORITES, null)
        if (favoritesJson != null) {
            val favoritesType = object : TypeToken<List<Quote>>() {}.type
            favoriteQuotes.value = Gson().fromJson(favoritesJson, favoritesType)
        }

        loadNextQuotes()
    }

    fun loadNextQuotes() {
        val startIndex = quoteIndex
        val endIndex = (quoteIndex + quotesPerPage).coerceAtMost(allQuotes.size)
        if (startIndex < endIndex) {
            currentQuotes.value = allQuotes.subList(startIndex, endIndex).toTypedArray()
            quoteIndex = endIndex
            isDataLoaded.value = true
        } else {
            // Reset to the beginning if we reach the end
            quoteIndex = 0
            loadNextQuotes()
        }
    }

    fun switchPages(quote: Quote) {
        currentQuote = quote
        currentPage.value = Pages.Details
    }

    fun backToList() {
        currentPage.value = Pages.Listings
    }

    fun resetQuotes() {
        loadNextQuotes()
    }

    fun saveQuoteIndex(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putInt(PREF_QUOTE_INDEX, quoteIndex)
            putString(PREF_FAVORITES, Gson().toJson(favoriteQuotes.value))
            apply()
        }
    }

    fun switchToFavorites() {
        currentPage.value = Pages.Favorites
    }

    fun addFavorite(quote: Quote) {
        favoriteQuotes.value = favoriteQuotes.value + quote
    }

    fun removeFavorite(quote: Quote) {
        favoriteQuotes.value = favoriteQuotes.value - quote
    }

    fun isFavorite(quote: Quote): Boolean {
        return favoriteQuotes.value.contains(quote)
    }
}
