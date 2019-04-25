package com.example.fragment.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.fragment.Models.Pokemon

import com.example.fragment.R
import kotlinx.android.synthetic.main.fragment_pokemon_details.view.*


class PokemonDetailsFragment : Fragment() {
    var pokemon = Pokemon(0,
            R.string.n_a_value.toString(),
            R.string.n_a_value.toString(),
            R.string.n_a_value.toString(),
            R.string.n_a_value.toString(),
            R.string.n_a_value.toString(),
            R.string.n_a_value.toString(),
            R.string.n_a_value.toString())

    companion object {
        fun newInstance(item: Pokemon): PokemonDetailsFragment{
            val newFragment = PokemonDetailsFragment()
            newFragment.pokemon = item
            return newFragment
        }
    }

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_pokemon_details, container, false)

        bindData(view)

        return view
    }

    fun bindData(view: View){
        view.pokemon_name_main_content_fragment.text = pokemon.name
        view.weight_main_content_fragment.text = pokemon.weight
        view.height_main_content_fragment.text = pokemon.height
        view.type_1_main_content_fragment.text = pokemon.fsttype
        view.type_2_main_content_fragment.text = pokemon.sndtype

        Glide.with(view.context)
            .load(pokemon.sprite)
            .placeholder(R.drawable.ic_launcher_background)
            .into(view.image_main_content_fragment)
    }

}
