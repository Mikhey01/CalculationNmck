package ru.rezvantsev.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.card_list_nmck.*
import ru.rezvantsev.myapplication.R
import ru.rezvantsev.myapplication.adapter.StepsAdapter
import ru.rezvantsev.myapplication.databinding.FragmentCurrentNMCKBinding
import ru.rezvantsev.myapplication.dto.DataNMCK
import ru.rezvantsev.myapplication.dto.StepNMCK
import ru.rezvantsev.myapplication.viewmodel.NmckViewModel


class CurrentNMCKFragment : Fragment() {
    private val args  by navArgs<CurrentNMCKFragmentArgs>()
    private val viewModel: NmckViewModel by activityViewModels()

    private lateinit var dataNMCK: DataNMCK

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

                    viewModel.navigateToOrganizationDataFragmentScreenEvent.observe(
                        viewLifecycleOwner
                    ) { initialContent ->
                        val direction =
                            CurrentNMCKFragmentDirections.currentNMCKFragmentToNewNMCKFragment(
                                initialContent.id,
                                NewNMCKFragment.REQUEST_CURRENT_RECIPE_KEY
                            )
                        findNavController().navigate(direction)
                    }


//                    viewModel.navigateToStepEditScreenEvent.observe(viewLifecycleOwner) { currentNMCK ->
//                        val directions =
//                            CurrentNMCKFragmentDirections.currentNMCKFragmentToStepNMCKFragment(
//
//                            )
//                        findNavController().navigate(directions)
//                    }


                    viewModel.navigateToStepAddScreenEvent.observe(
                        viewLifecycleOwner
                    ) {step ->
                               val direction =
                                   CurrentNMCKFragmentDirections.currentNMCKFragmentToStepNMCKFragment(
                                       step
                                   )
                                          findNavController().navigate(direction)
                    }

                    setFragmentResultListener(
                        requestKey = StepNMCKFragment.REQUEST_CURRENT_RECIPE_KEY
                    ) { requestKey, bundle ->
                        if (requestKey != StepNMCKFragment.REQUEST_CURRENT_RECIPE_KEY) return@setFragmentResultListener
                        val newTextStep   = bundle.getString (
                           StepNMCKFragment.RESULT_KEY

                        ) ?: return@setFragmentResultListener
                        viewModel.onSaveButtonStepClicked(newTextStep)
                    }

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
}




