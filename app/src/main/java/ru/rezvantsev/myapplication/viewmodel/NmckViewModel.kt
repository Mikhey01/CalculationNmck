package ru.rezvantsev.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.rezvantsev.myapplication.adapter.NmckInteractionListener
import ru.rezvantsev.myapplication.adapter.StepNMCKInteractionListener
import ru.rezvantsev.myapplication.dto.DataNMCK
import ru.rezvantsev.myapplication.dto.StepNMCK
import ru.rezvantsev.myapplication.repository.FileNMCKRepository
import ru.rezvantsev.myapplication.repository.NMCKRepository
import ru.rezvantsev.myapplication.util.SingleLiveEvent
import ru.rezvantsev.myapplication.util.StringArg

class NmckViewModel(
    application: Application
) : AndroidViewModel(application), NmckInteractionListener, StepNMCKInteractionListener {

    private val repository: NMCKRepository = FileNMCKRepository(application)
    val data by repository::data

    private val currentCalculation = MutableLiveData<DataNMCK?>(null)
    private val currentStepNMCK = MutableLiveData<StepNMCK?>(null)

    val navigateToOrganizationDataFragmentScreenEvent = SingleLiveEvent<DataNMCK>()
    val navigateToCurrentNMCKScreenEvent = SingleLiveEvent<DataNMCK>()
    private val navigateToStepEditScreenEvent = SingleLiveEvent<StepNMCK>()
    val navigateToStepAddScreenEvent = SingleLiveEvent<String>()

    fun onSaveButtonClicked(
        nameOrganization: String,
        nameAuthor: String,
        numberGuardsDuty : String,
        content: List<StepNMCK>?
    ) {
        val nmckForSave =
            currentCalculation.value?.copy(
                nameOrganization = nameOrganization,
                nameAuthor = nameAuthor,
                numberGuardsDuty = numberGuardsDuty,   //Lj.fdbk
                content = content
            ) ?: DataNMCK(
                id = NMCKRepository.NEW_DATANMCK_ID,
                nameOrganization = nameOrganization,
                nameAuthor = nameAuthor,
                numberGuardsDuty = numberGuardsDuty,   //Lj.fdbk
                content = content,
                //indexPosition = repository.getNextIndexId()
            )
        repository.save(nmckForSave)
        currentCalculation.value = null
    }

    fun onSaveButtonStepClicked( NumberGD: String,
      //NumberHoursPostSecurity: String,
//        NumberDaysPostSecurity: String,
//        NumberSecurityPosts: String,
//        Mrot: String,
//        ConsumerPriceIndex: String,
//        NumberHolidaysWeekends: String
    ) {
        if (NumberGD.isBlank()
//            ||
//            NumberHoursPostSecurity.isBlank()
//            || NumberDaysPostSecurity.isBlank() || NumberSecurityPosts.isBlank() || Mrot.isBlank()
//            || ConsumerPriceIndex.isBlank() || ConsumerPriceIndex.isBlank()
//            || NumberHolidaysWeekends.isBlank()
        ) return
        val stepForSave = currentStepNMCK.value?.copy(
            numberGuardsDuty = NumberGD,
//            numberHoursPostSecurity = NumberHoursPostSecurity,
//            numberDaysPostSecurity = NumberDaysPostSecurity,
//            numberSecurityPosts = NumberSecurityPosts,
//            mrot = Mrot,
//            // consumerPriceIndex = ConsumerPriceIndex.toString(),
//            numberHolidaysWeekends = NumberHolidaysWeekends
        ) ?: StepNMCK(
            idStep = FileNMCKRepository.NEW_STEP_ID,
            idDataNMCK = currentCalculation.value?.id ?: 0,
            numberGuardsDuty = NumberGD,
//            numberHoursPostSecurity = NumberHoursPostSecurity,
//            numberDaysPostSecurity = NumberDaysPostSecurity,
//            numberSecurityPosts = NumberSecurityPosts,
//            mrot = Mrot,
//            //    consumerPriceIndex = ConsumerPriceIndex.toString(),
//            numberHolidaysWeekends = NumberHolidaysWeekends
        )
        repository.saveStep(stepForSave)
        currentStepNMCK.value = null
        currentCalculation.value = null

    }

    fun onAddClicked() {
        navigateToOrganizationDataFragmentScreenEvent.call()
    }

    fun onAddStepClicked(dataNMCK: DataNMCK) {
        currentCalculation.value = dataNMCK
        navigateToStepAddScreenEvent.call()
    }

    override fun onRemoveClicked(dataNMCK: DataNMCK) = repository.delete(dataNMCK.id)

    override fun onEditClicked(dataNMCK: DataNMCK) {
        currentCalculation.value = dataNMCK
        navigateToOrganizationDataFragmentScreenEvent.value = dataNMCK
    }

    override fun onClicked(dataNMCK: DataNMCK) {
        navigateToCurrentNMCKScreenEvent.value = dataNMCK
    }

    override fun removeStepClicked(stepNMCK: StepNMCK) = repository.deleteStep(stepNMCK)

    override fun editStepClicked(stepNMCK: StepNMCK) {
        currentStepNMCK.value = stepNMCK
        navigateToStepEditScreenEvent.value = stepNMCK
    }

}

