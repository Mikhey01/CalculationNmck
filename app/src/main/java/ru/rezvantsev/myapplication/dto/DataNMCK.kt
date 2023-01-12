package ru.rezvantsev.myapplication.dto

import kotlinx.serialization.Serializable

@Serializable
data class DataNMCK(
    val id: Long,
    val nameOrganization: String,
    val nameAuthor: String,
   val content: List<StepNMCK>?
)
