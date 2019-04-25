package com.example.fragment.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fragment.Models.Pokemon
import com.example.fragment.R
import kotlinx.android.synthetic.main.list_item_pokemon.view.*
import kotlinx.android.synthetic.main.poke_item.view.*

class PokedexSimpleAdapter(var items: ArrayList<Pokemon>, val clickListener: (Pokemon)->Unit) : RecyclerView.Adapter<PokedexSimpleAdapter.ViewHolder>() , AdapterManagement{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_pokemon,parent, false)

        return ViewHolder(view)
    }


    override fun getItemCount() = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], clickListener)

    override fun changeDataSet(newDataSet: ArrayList<Pokemon>) {
        this.items = newDataSet
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(item: Pokemon, clickListener:  (Pokemon) -> Unit) = with(itemView){
            title_list_item.text = item.name
            url_list_item.text = item.url

            itemView.setOnClickListener{(clickListener(item))}
        }

    }

}