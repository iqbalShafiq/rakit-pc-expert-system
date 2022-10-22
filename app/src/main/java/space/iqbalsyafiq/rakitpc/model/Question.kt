package space.iqbalsyafiq.rakitpc.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
    val id: String,
    val no: String,
    val q: String,
    val qn: String,
    val question: String,
    val yes: String
) : Parcelable