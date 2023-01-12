package ru.rezvantsev.myapplication.adapter

import ru.rezvantsev.myapplication.dto.StepNMCK

interface StepNMCKInteractionListener {

    fun removeStepClicked(stepNMCK: StepNMCK)

    fun editStepClicked(stepNMCK: StepNMCK)


}