package ru.rezvantsev.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import ru.rezvantsev.myapplication.databinding.AddOrEditStepFragmentBinding
import ru.rezvantsev.myapplication.util.focusAndShowKeyboard

class StepNMCKFragment : Fragment() {

    private val args by navArgs<StepNMCKFragmentArgs>()
   // private val viewModel: NmckViewModel by activityViewModels()

    // private lateinit var currentNmck: StepNMCK


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = AddOrEditStepFragmentBinding.inflate(
        layoutInflater, container, false
    ).also { binding ->
        binding.NumberGuards.setText(args.forCurrentNMCKArgs)
//        binding.NumberSecurityHours.setAutoSizeTextTypeUniformWithConfiguration(args.forCurrentNMCK)
//        binding.NumberDaysProtection.setAutoSizeTextTypeUniformWithConfiguration(args.fromFragment3)
//        binding.NumberPosts.setAutoSizeTextTypeUniformWithConfiguration(args.fromFragment4)
//        binding.MinimumWage.setAutoSizeTextTypeUniformWithConfiguration(args.fromFragment5)
//        binding.NumberHolidaysWeekends.setAutoSizeTextTypeUniformWithConfiguration(args.fromFragment6)

        binding.NumberGuards.focusAndShowKeyboard()

        binding.button.setOnClickListener {

            onOkButtonClicked(binding)
        }
    }.root

    private fun onOkButtonClicked(binding: AddOrEditStepFragmentBinding) {
        val text = binding.NumberGuards.text.toString()
//        val text1 = binding.NumberSecurityHours.text.toString().toInt()
//        val text2 = binding.NumberDaysProtection.text.toString().toInt()
//        val text3 = binding.NumberPosts.text.toString().toInt()
//        val text4 = binding.MinimumWage.text.toString().toDouble()
//        val text5 = binding.NumberHolidaysWeekends.text.toString().toInt()
//        val stepNMCK = StepNMCK(text, text1, text2, text3, text4, text5)
        //  val action = StepNMCKFragmentD.currentNMCKFragmentToStepNMCKFragment(stepNMCK)

                val resultBundle = Bundle(1)
                resultBundle.putString(RESULT_KEY, text)
                setFragmentResult(
                    REQUEST_CURRENT_RECIPE_KEY,
                    resultBundle
                )
            }
            //   findNavController().navigate(action)



        companion object {
            const val REQUEST_CURRENT_RECIPE_KEY = "requestForCurrentRecipeFragmentKey"
            const val RESULT_KEY = "recipeNewContent"

        }
    }



