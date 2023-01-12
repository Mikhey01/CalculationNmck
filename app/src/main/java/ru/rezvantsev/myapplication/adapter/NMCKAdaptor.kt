package ru.rezvantsev.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.rezvantsev.myapplication.databinding.CardListNmckBinding
import ru.rezvantsev.myapplication.dto.DataNMCK

internal class NMCKAdaptor(
    private val interactionListener: NmckInteractionListener
) : ListAdapter<DataNMCK, NMCKAdaptor.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        fun inflater() = LayoutInflater.from(parent.context)
        val binding = CardListNmckBinding.inflate(inflater(), parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nmckItem = currentList[position]
        holder.bind(nmckItem)
    }

    inner class ViewHolder(
        private val binding: CardListNmckBinding,
        listener: NmckInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var dataNMCK: DataNMCK

        init {

            binding.deleted.setOnClickListener { listener.onRemoveClicked(dataNMCK) }
           // binding.root.setOnClickListener { listener.onClicked(dataNMCK) }
            binding.button.setOnClickListener {listener.onClicked(dataNMCK)}
        }

        fun bind(dataNMCK: DataNMCK) {
            this.dataNMCK = dataNMCK

            with(binding) {
                nameOrganizationEdit.text = dataNMCK.nameOrganization
                editNmckAuthor.text = dataNMCK.nameAuthor
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<DataNMCK>() {
        override fun areItemsTheSame(oldItem: DataNMCK, newItem: DataNMCK) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: DataNMCK, newItem: DataNMCK) =
            oldItem.content == newItem.content
    }
}


