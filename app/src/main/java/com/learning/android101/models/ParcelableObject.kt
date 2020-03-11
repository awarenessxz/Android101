package com.learning.android101.models

import android.os.Parcel
import android.os.Parcelable

class ParcelObj (val name: String?, var listItems : ArrayList<ParcelItem>?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        arrayListOf<ParcelItem>().apply {
            parcel.readList(this as List<ParcelItem>, ParcelItem::class.java.classLoader)
        }
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeList(listItems as List<ParcelItem>?)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelObj> {
        override fun createFromParcel(parcel: Parcel): ParcelObj {
            return ParcelObj(parcel)
        }

        override fun newArray(size: Int): Array<ParcelObj?> {
            return arrayOfNulls(size)
        }
    }

    fun displayValues() : String {
        return "${name} ${listItems?.size}"
    }
}

class ParcelItem (val title: String?, val age: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(age)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelItem> {
        override fun createFromParcel(parcel: Parcel): ParcelItem {
            return ParcelItem(parcel)
        }

        override fun newArray(size: Int): Array<ParcelItem?> {
            return arrayOfNulls(size)
        }
    }
}