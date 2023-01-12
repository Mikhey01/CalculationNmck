package ru.rezvantsev.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.rezvantsev.myapplication.databinding.OrganizationDataFragmentBinding
import ru.rezvantsev.myapplication.dto.DataNMCK
import ru.rezvantsev.myapplication.util.focusAndShowKeyboard
import ru.rezvantsev.myapplication.viewmodel.NmckViewModel

class NewNMCKFragment : Fragment() {

    private val viewModel: NmckViewModel by activityViewModels()
    private val args by navArgs<NewNMCKFragmentArgs>()

    private lateinit var currentNmck: DataNMCK

  //  private lateinit var item: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = OrganizationDataFragmentBinding.inflate(layoutInflater, container, false)
        .also { binding ->
            if (args.fromFragment == REQUEST_CURRENT_RECIPE_KEY) {
                currentNmck = viewModel.data.value?.let { listNmck ->
                    listNmck.firstOrNull {
                        it.id == args.initialContentNmck
                    }
                } as DataNMCK

                binding.nameOrganizationEdit.setText(currentNmck.nameOrganization)
                binding.nmckAuthorEdit.setText(currentNmck.nameAuthor)
//                // val categoriesList = resources.getStringArray(R.array.category_names)
//                //  val numberCategory = categoriesList.indexOf(currentRecipe.recipeCategory)
//                //binding.editCategory.setSelection(numberCategory)
           }
            binding.nameOrganizationEdit.focusAndShowKeyboard()

            binding.ok.setOnClickListener {
                onOkButtonClicked(binding)
            }
        }.root

    private fun onOkButtonClicked(binding: OrganizationDataFragmentBinding) {
        val textTitle = binding.nameOrganizationEdit.text.toString()
        val textAuthor = binding.nmckAuthorEdit.text.toString()
        //  val textCategory = item

        if (!textTitle.isBlank()) {
            if (args.fromFragment == REQUEST_FEED_KEY
//            ||
//               args.fromFragment == REQUEST_FEED_FAVORITE_KEY
            ) {
                viewModel.onSaveButtonClicked(
                    textTitle,
                    textAuthor,
                    null
                )
            } else if (args.fromFragment == REQUEST_CURRENT_RECIPE_KEY) {
                viewModel.onSaveButtonClicked(
                    textTitle,
                    textAuthor,
                    currentNmck.content
                )
            }
        }
        findNavController().navigateUp()
        //popBackStack()
    }

    companion object {
        const val REQUEST_FEED_KEY = "requestForFeedFragmentKey"
        const val REQUEST_CURRENT_RECIPE_KEY = "requestForCurrentRecipeFragmentKey"
        // const val REQUEST_FEED_FAVORITE_KEY = "requestForFeedFavoriteFragmentKey"

//        var Bundle.textArg: String? by StringArg.StringArg
//
//        var Bundle.longArg: Long by StringArg.LongArg

    }
}