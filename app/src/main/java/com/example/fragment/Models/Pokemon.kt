package com.example.fragment.Models

import android.os.Parcel
import android.os.Parcelable

data class Pokemon(
    val name: String,
    val url: String
): Parcelable {

    constructor(parcel: Parcel) : this(
        name = parcel.readString(),
        url = parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pokemon> {
        override fun createFromParcel(parcel: Parcel): Pokemon {
            return Pokemon(parcel)
        }

        override fun newArray(size: Int): Array<Pokemon?> {
            return arrayOfNulls(size)
        }
    }

}