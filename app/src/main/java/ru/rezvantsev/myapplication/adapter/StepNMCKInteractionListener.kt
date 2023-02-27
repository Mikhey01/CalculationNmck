package ru.rezvantsev.myapplication.adapter

import android.text.Editable
import ru.rezvantsev.myapplication.dto.StepNMCK

interface StepNMCKInteractionListener {

    fun removeStepClicked(stepNMCK: StepNMCK)

    fun editStepClicked(stepNMCK: StepNMCK)
    //fun onOkButtonClicked(text: Editable, text1: Editable?, text2: Editable?, text3: Editable?, text4: Editable?, text5: Editable?)


}