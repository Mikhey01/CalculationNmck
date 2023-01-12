package ru.rezvantsev.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ru.rezvantsev.myapplication.R
import ru.rezvantsev.myapplication.adapter.NMCKAdaptor
import ru.rezvantsev.myapplication.adapter.StepsAdapter
import ru.rezvantsev.myapplication.databinding.FragmentCurrentNMCKBinding
import ru.rezvantsev.myapplication.dto.DataNMCK
import ru.rezvantsev.myapplication.dto.StepNMCK
import ru.rezvantsev.myapplication.ui.StepNMCKFragment.Companion.RESULT_KEY
import ru.rezvantsev.myapplication.viewmodel.NmckViewModel


class CurrentNMCKFragment : Fragment() {

    private val viewModel: NmckViewModel by activityViewModels()
    private val args by navArgs<CurrentNMCKFragmentArgs>()
    private lateinit var dataNMCK: DataNMCK

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
            return FragmentCurrentNMCKBinding.inflate(layoutInflater, container, false)
        .also { binding ->
            with(binding) {
                //    stepsRecyclerViewAdapter = NMCKAdaptor(viewModel)
                val adapter = StepsAdapter(viewModel)
                stepsRecyclerView.adapter = adapter

                dataNMCK = viewModel.data.value?.let { listNMCK ->
                    listNMCK.firstOrNull {
                        it.id == args.idCurrentNMCK
                    }
                } as DataNMCK
                render(dataNMCK)
                adapter.submitList(dataNMCK.content)
                adapter.differ.submitList(dataNMCK.content)

                viewModel.data.observe(viewLifecycleOwner) { listNMCK ->
                    if (listNMCK.none { it.id == args.idCurrentNMCK }) {
                        return@observe
                    }
                    dataNMCK = listNMCK.firstOrNull {
                        it.id == args.idCurrentNMCK
                    } as DataNMCK
                    render(dataNMCK)
                    adapter.submitList(dataNMCK.content)
                    adapter.differ.submitList(dataNMCK.content)
                }

                viewModel.navigateToOrganizationDataFragmentScreenEvent.observe(viewLifecycleOwner) { initialContent ->
                    val direction =
                        CurrentNMCKFragmentDirections.currentNMCKFragmentToNewNMCKFragment(
                            initialContent.id,
                            NewNMCKFragment.REQUEST_CURRENT_RECIPE_KEY
                        )
                    findNavController().navigate(direction)
                }

                viewModel.navigateToStepEditScreenEvent.observe(viewLifecycleOwner) { currentNMCK ->
                    val directions =
                        CurrentNMCKFragmentDirections.currentNMCKFragmentToStepNMCKFragment(
                            currentNMCK.numberGuardsDuty,
                            currentNMCK.numberHoursPostSecurity,
                            currentNMCK.numberDaysPostSecurity,
                            currentNMCK.nightShifts,
                            currentNMCK.numberSecurityPosts,
                            currentNMCK.mrot,
                            currentNMCK.consumerPriceIndex,
                            currentNMCK.correctionFactorUd1,
                            currentNMCK.correctionFactorUd2,
                            currentNMCK.correctionFactorUd3,
                            currentNMCK.numberHolidaysWeekends
                        )
                    findNavController().navigate(directions)
                }

                viewModel.navigateToStepAddScreenEvent.observe(
                    viewLifecycleOwner
                ) { currentNMCK ->
                    val direction =
                        CurrentNMCKFragmentDirections.currentNMCKFragmentToStepNMCKFragment(
                            currentNMCK,
                            currentNMCK,
                            currentNMCK,
                            currentNMCK.toBoolean(),
                            currentNMCK,
                            currentNMCK,
                            currentNMCK,
                            currentNMCK.toBoolean(),
                            currentNMCK.toBoolean(),
                            currentNMCK.toBoolean(),
                            currentNMCK

                            )
                    findNavController().navigate(direction)
                }
                setFragmentResultListener(
                    requestKey = StepNMCKFragment.REQUEST_CURRENT_RECIPE_KEY
                ) { requestKey, bundle ->
                    if (requestKey != StepNMCKFragment.REQUEST_CURRENT_RECIPE_KEY) return@setFragmentResultListener
                    val newTextStep = bundle.getBundle(
                        RESULT_KEY,
                    ) ?: return@setFragmentResultListener
                    viewModel.onSaveButtonStepClicked(bundle)
                }

                //   likeIcon.setOnClickListener { viewModel.onAddFavoriteClicked(currentNMCK) }

//                binding.stepsRecyclerView.layoutManager = LinearLayoutManager(context)
//                binding.stepsRecyclerView.adapter = stepsRecyclerViewAdapter
//
//                viewModel.data.observe(viewLifecycleOwner) { nmcks ->
//                    stepsRecyclerViewAdapter.submitList(nmcks)
//                }

                fabSteps.setOnClickListener {
                    viewModel.onAddStepClicked(dataNMCK)
                }

                val popupMenu by lazy {
                    PopupMenu(context, binding.options).apply {
                        inflate(R.menu.options_menu)
                        setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.remove -> {
                                    viewModel.onRemoveClicked(dataNMCK)
                                    findNavController().popBackStack()
                                    true
                                }
                                R.id.edit -> {
                                    viewModel.onEditClicked(dataNMCK)
                                    true
                                }
                                else -> false
                            }
                        }
                    }
                }

                options.setOnClickListener { popupMenu.show() }
            }

        }.root
}

private fun FragmentCurrentNMCKBinding.render(dataNMCK: DataNMCK) {

    nameOrganization.text = dataNMCK.nameOrganization
    title.text = dataNMCK.nameAuthor


}




