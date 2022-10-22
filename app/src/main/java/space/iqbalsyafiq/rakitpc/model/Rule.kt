package space.iqbalsyafiq.rakitpc.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rule(
    val cpu: String,
    val casing: String,
    val coolerCPU: String,
    val gpu: String,
    val totalPrice: String,
    val motherboard: String,
    val output: String,
    val powerSupply: String,
    val ram: String,
    val rules: String,
    val storage: String,
    val description: String
) : Parcelable