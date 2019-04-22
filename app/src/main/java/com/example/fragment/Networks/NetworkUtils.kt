package com.example.fragment.Networks

import android.net.Uri
import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class NetworkUtils {
    val POKEMON_API_BASE_URL = "https://pokeapi.co/api/v2/"
    val POKEMON_INFO = "pokemon"
    val POKEMON_TYPE = "type"

    private val TAG = NetworkUtils::class.java.simpleName

    fun buildUrl(root: String, pokeID: String): URL {
        val builtUri = Uri.parse(POKEMON_API_BASE_URL)
            .buildUpon()
            .appendPath(root)
            .appendPath(pokeID)
            .build()

        val url = try {
            URL(builtUri.toString())
        } catch (e: MalformedURLException) {
            URL("")
        }

        Log.d(TAG, "Built URI $url")

        return url
    }

    @Throws(IOException::class)
    fun getResponseFromHttpUrl(url: URL): String {
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val `in` = urlConnection.inputStream

            val scanner = Scanner(`in`)
            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            return if (hasInput) {
                scanner.next()
            } else {
                ""
            }
        } finally {
            urlConnection.disconnect()
        }
    }
}