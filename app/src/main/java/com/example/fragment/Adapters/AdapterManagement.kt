package com.example.fragment.Adapters

import com.example.fragment.Models.Pokemon

interface AdapterManagement {
    fun changeDataSet(newDataSet : ArrayList<Pokemon>)
}