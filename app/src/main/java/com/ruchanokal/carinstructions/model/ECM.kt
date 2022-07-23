package com.ruchanokal.carinstructions.model

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
class ECM(val marka: String?, val ecm : String?,
          val aractipi : String?, val aracmodeli : String?,
          val url : String?) : Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }
}