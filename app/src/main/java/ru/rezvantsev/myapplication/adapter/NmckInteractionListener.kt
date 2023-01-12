package ru.rezvantsev.myapplication.adapter

import ru.rezvantsev.myapplication.dto.DataNMCK

interface NmckInteractionListener {

    fun onRemoveClicked(dataNMCK: DataNMCK)
    fun onEditClicked(dataNMCK: DataNMCK)
    fun onClicked(dataNMCK: DataNMCK)
}