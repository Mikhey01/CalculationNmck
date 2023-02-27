package ru.rezvantsev.myapplication.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.rezvantsev.myapplication.R
import ru.rezvantsev.myapplication.databinding.StepCalculationBinding
import ru.rezvantsev.myapplication.dto.StepNMCK

internal class StepsAdapter(
    private val interactionListener: StepNMCKInteractionListener
) : ListAdapter<StepNMCK, StepsAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StepCalculationBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stepItem = currentList[position]
        holder.bind(stepItem)
    }

    inner class ViewHolder(
        private val binding: StepCalculationBinding,
        listener: StepNMCKInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var stepNMCK: StepNMCK

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.optionsStep).apply {
                inflate(R.menu.options_menu)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.removeStepClicked(stepNMCK)
                            true
                        }
                        R.id.edit -> {
                            listener.editStepClicked(stepNMCK)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.optionsStep.setOnClickListener { popupMenu.show() }
        }

        @SuppressLint("SetTextI18n")
        fun bind(stepNMCK: StepNMCK) {
            this.stepNMCK = stepNMCK

            with(binding) {
                NumberGuards.text = stepNMCK.numberGuardsDuty
//                NumberSecurityHours.text = stepNMCK.numberHoursPostSecurity.toString()
//                NumberDaysProtection.text = stepNMCK.numberDaysPostSecurity.toString()
//                NumberPosts.text = stepNMCK.numberSecurityPosts.toString()
//                MinimumWage.text = stepNMCK.mrot.toString()
//                NumberHolidaysWeekends.text = stepNMCK.numberHolidaysWeekends.toString()
                //textCategoryFilter.text = stepNMCK.nightShifts.toString()
              //  if (!stepNMCK.nightShifts) textCategoryFilter.text = "НЕТ" else "ДА"
               // if (!stepNMCK.correctionFactorUd1) specialEquipment.text = "НЕТ" else "ДА"
              //  if (!stepNMCK.correctionFactorUd2) massEvents.text = "НЕТ" else "ДА"
             //   if (!stepNMCK.correctionFactorUd3) antiTerroristSecurity.text = "НЕТ" else "ДА"
//                stepContent.text = stepNMCK.stepText
//                imageStep.isVisible = step.picture.isNotBlank()
//                if (step.picture.isNotBlank()) imageStep.setImageURI(
//                    step.picture.toUri()
                //)
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<StepNMCK>() {

        override fun areItemsTheSame(oldItem: StepNMCK, newItem: StepNMCK) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: StepNMCK, newItem: StepNMCK) =
            oldItem.numberGuardsDuty == newItem.numberGuardsDuty
                    //&&                    oldItem.numberHoursPostSecurity == newItem.numberHoursPostSecurity &&
//                    oldItem.numberDaysPostSecurity == newItem.numberDaysPostSecurity &&
//                    oldItem.numberSecurityPosts == newItem.numberSecurityPosts &&
//                    oldItem.mrot == newItem.mrot &&
//                    oldItem.numberHolidaysWeekends == newItem.numberHolidaysWeekends
    }

    val differ = AsyncListDiffer(this, DiffCallback)
}


