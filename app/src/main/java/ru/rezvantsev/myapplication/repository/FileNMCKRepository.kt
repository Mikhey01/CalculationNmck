package ru.rezvantsev.myapplication.repository

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.rezvantsev.myapplication.dto.DataNMCK
import ru.rezvantsev.myapplication.dto.StepNMCK
import kotlin.properties.Delegates

class FileNMCKRepository(
    private val application: Application
) : NMCKRepository {

    private val gson = Gson()
    private val type = TypeToken.getParameterized(List::class.java, DataNMCK::class.java).type

    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    )

    private var nextId: Long by Delegates.observable(
        prefs.getLong(NEXT_ID_PREFS_KEY, 0L)
    ) { _, _, newValue ->
        prefs.edit { putLong(NEXT_ID_PREFS_KEY, newValue) }
    }


    private var nmcks
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }
        set(value) {
            application
                .openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
                .bufferedWriter().use {
                    it.write(gson.toJson(value))
                }
            data.value = value
        }
    override val data: MutableLiveData<List<DataNMCK>>

    init {
        val nmcksFile = application.filesDir.resolve(FILE_NAME)
        val nmcks: List<DataNMCK> = if (nmcksFile.exists()) {
            val inputStream = application.openFileInput(FILE_NAME)
            val reader = inputStream.bufferedReader()
            reader.use {
                gson.fromJson(it, type)
            }
        } else emptyList()
        data = MutableLiveData(nmcks)
    }

    override fun getNextIndexId(): Long {
        TODO("Not yet implemented")
    }

    override fun delete(nmckId: Long) {
        nmcks = nmcks.filter {
            (it.id != nmckId)
        }
        data.value = nmcks
    }

    fun insert(dataNMCK: DataNMCK) {
        data.value = listOf(
            dataNMCK.copy(
                id = ++nextId
            )
        ) + nmcks
    }

    fun update(dataNMCK: DataNMCK) {
        nmcks = nmcks.map {
            if (it.id == dataNMCK.id) dataNMCK else it
        }
    }

    override fun save(dataNMCK: DataNMCK) {
        if (dataNMCK.id == NMCKRepository.NEW_DATANMCK_ID) insert(dataNMCK) else update(dataNMCK)

//        fun removeById(postId: Long) {
//            nmcks = nmcks.filter {
//                (it.id != postId)
//            }
//            //   data.value = posts
//        }

//         fun cancel() {
//            data.value = posts
//        }
    }

    override fun deleteStep(stepNMCK: StepNMCK) {
        TODO("Not yet implemented")
    }

    override fun saveStep(stepNMCK: StepNMCK) {
        TODO("Not yet implemented")
    }

//    override fun getFilteredList(filters: MutableSet<String>?): LiveData<List<DataNMCK>> {
//        if (filters.isNullOrEmpty()) {
//            return data
//        }
//        val filteredNMCK = data.map{ nmckList ->
//            val newNmck = nmckList.filter {
//                it.nameOrganization in filters
//            }
//            newNmck
//        }
//        return filteredNMCK

//    }

    companion object {
        const val NEXT_ID_PREFS_KEY = "nextId"
        const val FILE_NAME = "posts.json"
        // const val GENERATED_POSTS_AMOUNT = 1000


        //    const val NEW_RECIPE_ID = 0L
          //  const val NEW_STEP_ID = 0L

    }
}