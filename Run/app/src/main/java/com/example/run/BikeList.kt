package com.example.run

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class BikeList(val bikes: List<Bike>): Parcelable {
}