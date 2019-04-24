package com.example.fragment.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.fragment.R

class PokemonViewer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewer_pokemon)
    }
}
