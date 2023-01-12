package ru.rezvantsev.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.rezvantsev.myapplication.adapter.NMCKAdaptor
import ru.rezvantsev.myapplication.databinding.FeedFragmentBinding
import ru.rezvantsev.myapplication.ui.NewNMCKFragment
import ru.rezvantsev.myapplication.viewmodel.NmckViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FeedFragment : Fragment() {

    private val viewModel: NmckViewModel by activityViewModels()

    private lateinit var recyclerViewAdapter: NMCKAdaptor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigateToOrganizationDataFragmentScreenEvent.observe(this) {
            val direction = FeedFragmentDirections.actionFeedFragmentToNewNMCKFragment(
                0,
                NewNMCKFragment.REQUEST_FEED_KEY
            )
            findNavController().navigate(direction)
        }

        viewModel.navigateToCurrentNMCKScreenEvent.observe(this) { currentNMCK ->
            val direction =
                FeedFragmentDirections.actionFeedFragmentToCurrentNMCKFragment(currentNMCK.id)
            findNavController().navigate(direction)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FeedFragmentBinding.inflate(layoutInflater, container, false)
        .also { binding ->
            recyclerViewAdapter = NMCKAdaptor(viewModel)

//            viewModel.filterResult.observe(viewLifecycleOwner) { recipes ->
//            if (recipes.isNullOrEmpty()) binding.emptyStateGroup.visibility = View.VISIBLE
//            else binding.emptyStateGroup.visibility = View.GONE
//            recyclerViewAdapter.submitList(recipes)
//        }

            binding.list.layoutManager = LinearLayoutManager(context)
            binding.list.adapter = recyclerViewAdapter

            viewModel.data.observe(viewLifecycleOwner) { nmcks ->
                recyclerViewAdapter.submitList(nmcks)
            }

            binding.fab.setOnClickListener {
                viewModel.onAddClicked()
            }

        }.root
}