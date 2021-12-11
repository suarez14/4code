package com.example.run

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Bike(
    var nombre: String,
    var marca: String,
    var modelo: Int,
    var kms: Int,
    var cilindraje: String,
    var precio: Int,
    var imgUrl: String
): Parcelable {
}