package ru.rezvantsev.myapplication.repository

import androidx.lifecycle.LiveData
import ru.rezvantsev.myapplication.dto.DataNMCK
import ru.rezvantsev.myapplication.dto.StepNMCK

interface NMCKRepository {

    val data: LiveData<List<DataNMCK>>

    // fun shareById(recipeId: Long)

    fun getNextIndexId(): Long

    fun delete(nmckId: Long)

    fun save(dataNMCK: DataNMCK)

    //fun updateListOnMove(from: Long, to: Long, fromId: Long, toId: Long)

    fun deleteStep(stepNMCK: StepNMCK)

    fun saveStep(stepNMCK: StepNMCK)

//    fun getFilteredList(
//        filters: MutableSet<String>?
//    ): LiveData<List<DataNMCK>>

    companion object {
        const val NEW_DATANMCK_ID = 0L
        const val NEW_STEP_ID = 0L
    }
}