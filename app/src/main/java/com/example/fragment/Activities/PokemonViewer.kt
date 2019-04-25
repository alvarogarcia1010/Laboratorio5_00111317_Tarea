package com.example.fragment.Activities

import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fragment.Models.Pokemon
import com.example.fragment.Networks.NetworkUtils
import com.example.fragment.R
import kotlinx.android.synthetic.main.viewer_pokemon.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class PokemonViewer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewer_pokemon)

        val uri:String = this.intent.extras.getString("URL")
        //setSupportActionBar(toolbarviewer)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //supportActionBar?.setDisplayShowHomeEnabled(true)
        collapsingtoolbarviewer.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        collapsingtoolbarviewer.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)

        FetchPokemonTask().execute(uri)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item!!.itemId){
            android.R.id.home -> {onBackPressed();true}
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun init(pokemon: Pokemon){
        Glide.with(this)
            .load(pokemon.sprite)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(app_bar_image_viewer)

        collapsingtoolbarviewer.title = pokemon.name
        weight_main_content_fragment.text = pokemon.weight
        height_main_content_fragment.text = pokemon.height
        type_1_main_content_fragment.text = pokemon.fsttype
        type_2_main_content_fragment.text = pokemon.sndtype
    }

    private inner class FetchPokemonTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg query: String): String {

            if (query.isNullOrEmpty()) return ""

            val url = query[0]
            val pokeAPI = Uri.parse(url).buildUpon().build()
            val finalurl = try {
                URL(pokeAPI.toString())
            } catch (e: MalformedURLException){
                URL("")
            }

            return try {
                NetworkUtils().getResponseFromHttpUrl(finalurl)
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }

        }

        override fun onPostExecute(pokemonInfo: String) {
            val pokemon = if (!pokemonInfo.isEmpty()) {
                val root = JSONObject(pokemonInfo)
                val sprites = root.getString("sprites")
                val types = root.getJSONArray("types")
                val fsttype = JSONObject(types[0].toString()).getString("type")
                val sndtype = try { JSONObject(types[1].toString()).getString("type") }catch (e: JSONException){ "" }

                Pokemon(root.getInt("id"),
                    root.getString("name").capitalize(),
                    root.getString("location_area_encounters"),
                    JSONObject(fsttype).getString("name").capitalize(),
                    if(sndtype.isEmpty()) " " else JSONObject(sndtype).getString("name").capitalize(),
                    root.getString("weight"), root.getString("height"),
                    JSONObject(sprites).getString("front_default"))

            } else {
                Pokemon(0, R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(),R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString(), R.string.n_a_value.toString())
            }
            init(pokemon)
        }
    }
}
