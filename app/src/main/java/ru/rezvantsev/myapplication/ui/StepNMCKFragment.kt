package ru.rezvantsev.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.rezvantsev.myapplication.databinding.AddOrEditStepFragmentBinding
import ru.rezvantsev.myapplication.dto.StepNMCK
import ru.rezvantsev.myapplication.util.focusAndShowKeyboard
import ru.rezvantsev.myapplication.viewmodel.NmckViewModel

class StepNMCKFragment : Fragment() {

    private val args by navArgs<StepNMCKFragmentArgs>()
    private val viewModel: NmckViewModel by activityViewModels()

    private lateinit var currentNmck: StepNMCK

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = AddOrEditStepFragmentBinding.inflate(
        layoutInflater, container, false
    ).also { binding ->
            binding.NumberGuards.setText(currentNmck.numberGuardsDuty)
            binding.NumberSecurityHours.setText(currentNmck.numberHoursPostSecurity)
            binding.NumberDaysProtection.setText(currentNmck.numberDaysPostSecurity)
            binding.NumberPosts.setText(currentNmck.numberSecurityPosts)
            binding.NumberHolidaysWeekends.setText(currentNmck.numberHolidaysWeekends)
            binding.MinimumWage.setText(currentNmck.mrot.toInt())

        binding.NumberGuards.focusAndShowKeyboard()
        binding.button.setOnClickListener {
            onOkButtonClicked(binding)
        }
    }.root

    private fun onOkButtonClicked(binding: AddOrEditStepFragmentBinding) {

        val NumberGuardsDuty = binding.NumberGuards.text.toString()
        val NumberHoursPostSecurity = binding.NumberSecurityHours.text.toString()
        val NumberDaysPostSecurity = binding.NumberDaysProtection.text.toString()
        val NumberSecurityPosts = binding.NumberPosts.text.toString()
        val NumberHolidaysWeekends = binding.NumberHolidaysWeekends.text.toString()
        val MROT = binding.MinimumWage.text.toString()

        if (!NumberGuardsDuty.isBlank()) {
            if (args.fromFragment1 == RESULT_KEY

            ) {
                viewModel.onSaveButtonStepClicked(
                    NumberGuardsDuty,
                    NumberHoursPostSecurity,
                    NumberDaysPostSecurity,
                    NumberSecurityPosts,
                    MROT,
                    "1",
                    false.toString(),
                    false.toString(),
                    false.toString(),
                    NumberHolidaysWeekends,

                    )
            }
        }
        findNavController().popBackStack()
    }


    companion object {
        const val REQUEST_CURRENT_RECIPE_KEY = "requestForCurrentRecipeFragmentKey"
        const val RESULT_KEY = "recipeNewContent"
    }
}



