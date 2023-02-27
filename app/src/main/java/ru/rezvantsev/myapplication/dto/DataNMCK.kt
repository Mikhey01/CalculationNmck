package ru.rezvantsev.myapplication.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class DataNMCK(
    val id: Long,
    val nameOrganization: String,
    val nameAuthor: String,
   val content: List<StepNMCK>?
) : Parcelable
