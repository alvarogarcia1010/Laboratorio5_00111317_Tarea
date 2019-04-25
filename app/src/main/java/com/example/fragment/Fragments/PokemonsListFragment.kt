package com.example.fragment.Fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fragment.Adapters.AdapterManagement
import com.example.fragment.Adapters.PokedexAdapter
import com.example.fragment.Adapters.PokedexSimpleAdapter
import com.example.fragment.Models.Pokemon

import com.example.fragment.R
import com.example.fragment.Utils.AppConstants
import kotlinx.android.synthetic.main.fragment_pokemons_list.view.*

class PokemonsListFragment : Fragment() {

    private lateinit var  pokemonList :ArrayList<Pokemon>
    private lateinit var PokedexAdapter : AdapterManagement
    var listenerTools :  ListenerTools? = null

    companion object {
        fun newInstance(dataset : ArrayList<Pokemon>): PokemonsListFragment{
            val newFragment = PokemonsListFragment()
            newFragment.pokemonList = dataset
            return newFragment
        }
    }

    interface ListenerTools{
        fun managePortraitItemClick(item: Pokemon)

        fun manageLandscapeItemClick(item: Pokemon)

        fun searchPokemon(pokemonName: String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val view = inflater.inflate(R.layout.fragment_pokemons_list, container, false)

        if(savedInstanceState != null) pokemonList = savedInstanceState.getParcelableArrayList<Pokemon>(AppConstants.MAIN_LIST_KEY)!!

        initRecyclerView(resources.configuration.orientation, view)
        initSearchButton(view)

        return view
    }

    fun initRecyclerView(orientation:Int, container:View){
        val linearLayoutManager = LinearLayoutManager(this.context)

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            PokedexAdapter = PokedexAdapter(pokemonList, { item:Pokemon -> listenerTools?.managePortraitItemClick(item)})
            container.rv_pokemon_list.adapter = PokedexAdapter as PokedexAdapter

        }
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            PokedexAdapter = PokedexSimpleAdapter(pokemonList, {item:Pokemon->listenerTools?.manageLandscapeItemClick(item)})
            container.rv_pokemon_list.adapter = PokedexAdapter as PokedexSimpleAdapter

        }


        container.rv_pokemon_list.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }
    }

    fun initSearchButton(container:View) = container.btn_search_pokemon.setOnClickListener {
        listenerTools?.searchPokemon(container.et_pokemon_name.text.toString())
    }

    fun updatePokemonList(pokemonList: ArrayList<Pokemon>){ PokedexAdapter.changeDataSet(pokemonList) }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is ListenerTools) {
            listenerTools = context
        } else {
            throw RuntimeException("Se necesita una implementación de  la interfaz")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(AppConstants.MAIN_LIST_KEY, pokemonList)
        super.onSaveInstanceState(outState)
    }

    override fun onDetach() {
        super.onDetach()
        listenerTools = null
    }
}