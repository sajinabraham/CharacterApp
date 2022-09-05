package com.tcl.characterapp.presentation.character.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tcl.characterapp.R
import com.tcl.characterapp.databinding.CharacterItemListBinding
import com.tcl.characterapp.domain.model.CharactersDomain
import com.tcl.characterapp.presentation.character.view.CharacterFragmentDirections
import com.tcl.characterapp.presentation.character.viewmodel.states.ListType
import com.tcl.characterapp.utils.ItemLongClickListener


const val GRID_LAYOUT = 0

class CharacterAdapter(
    private val onLongClickListener: ItemLongClickListener,
    private var listType: ListType = ListType.GridLayout
) :
    PagingDataAdapter<CharactersDomain, RecyclerView.ViewHolder>(DiffUtilCallBack()) {

    fun setListType(listType: ListType) {
        this.listType = listType
    }


    class CharacterViewHolder(val binding: CharacterItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.characterModel?.id?.let { id ->
                    navigateToCharacterDetail(id, it)
                }
            }

        }

        private fun navigateToCharacterDetail(id: Int, view: View) {
            val direction =
                CharacterFragmentDirections.actionCharacterFragmentToCharacterDetailFragment(
                    id
                )
            view.findNavController().navigate(direction)
        }

        fun bind(characterModel: CharactersDomain) {
            binding.characterModel = characterModel
            binding.executePendingBindings()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CharacterViewHolder(
            CharacterItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemViewType(position: Int): Int = 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val characterModel = getItem(position)
        holder as CharacterViewHolder
        holder.bind(characterModel!!)

        holder.itemView.animation = AnimationUtils.loadAnimation(
            holder.itemView.context,
            R.anim.scale_up
        )

        holder.itemView.setOnLongClickListener {
            onLongClickListener.onLongClick(characterModel)
            it == it
        }
    }

}

class DiffUtilCallBack : DiffUtil.ItemCallback<CharactersDomain>() {
    override fun areItemsTheSame(oldItem: CharactersDomain, newItem: CharactersDomain): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharactersDomain, newItem: CharactersDomain): Boolean {
        return oldItem == newItem
    }

}

const val FROMCHARACTERLIST = "fromCharacterList"
const val FROMFAVORITELIST = "fromFavoriteList"