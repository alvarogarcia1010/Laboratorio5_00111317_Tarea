package com.example.fragment.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.fragment.Models.Pokemon
import com.example.fragment.R
import kotlinx.android.synthetic.main.poke_item.view.*

class PokedexAdapter(var items: ArrayList<Pokemon>, val clickListener: (Pokemon)->Unit) : RecyclerView.Adapter<PokedexAdapter.ViewHolder>() , AdapterManagement{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.poke_item,parent, false)

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
            tv_poke_name.text = item.name
            tv_poke_url.text = item.url

            Glide.with(itemView.context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ getNumber(item.url)+".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.poke_icon)

            itemView.setOnClickListener{(clickListener(item))}
        }

        fun getNumber(url: String): Int {
            val urlPart = url.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            return Integer.parseInt(urlPart[urlPart.size - 1])
        }
    }

}