package com.tcl.characterapp.presentation.favorite.view

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tcl.characterapp.databinding.FragmentFavoriteBinding
import com.tcl.characterapp.presentation.favorite.adapter.FavoriteCharacterAdapter
import com.tcl.characterapp.presentation.favorite.viewModel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoriteCharacterAdapter
    private val viewModel: FavoriteViewModel by viewModels()

    private val swipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val character = adapter.currentList[layoutPosition]
            viewModel.deleteCharacter(character)
        }


        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {

//            RecyclerViewSwipeDecorator.Builder(
//                c,
//                recyclerView,
//                viewHolder,
//                dX,
//                dY,
//                actionState,
//                isCurrentlyActive
//            )
//                .addSwipeLeftLabel(getString(R.string.swipe_delete))
//                .addSwipeLeftActionIcon(R.drawable.ic_delete)
//                .setSwipeLeftActionIconTint(resources.getColor(R.color.white))
//                .setSwipeLeftLabelColor(resources.getColor(R.color.white))
//                .addSwipeLeftBackgroundColor(resources.getColor(R.color.red))
//                .create()
//                .decorate()
//
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareAdapter()
        lifecycleScope.launch {
            viewModel.getFavoriteCharacters().collect {
                adapter.submitList(it)
            }
        }


    }

    private fun prepareAdapter() {
        adapter = FavoriteCharacterAdapter()
        binding.characterList.adapter = adapter
        binding.characterList.layoutManager = LinearLayoutManager(requireContext())

        ItemTouchHelper(swipeCallback).attachToRecyclerView(binding.characterList)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}