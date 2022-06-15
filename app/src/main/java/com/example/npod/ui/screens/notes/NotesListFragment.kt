package com.example.npod.ui.screens.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.npod.R
import com.example.npod.app
import com.example.npod.data.AppState
import com.example.npod.databinding.FragmentNotesListBinding
import com.example.npod.domain.notes.Note
import com.example.npod.ui.AdapterItem
import com.example.npod.ui.AppRecyclerAdapter
import com.example.npod.ui.NoteViewModelFabric

class NotesListFragment : Fragment(R.layout.fragment_notes_list) {
    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: AppRecyclerAdapter

    private val viewModel: NoteViewModel by viewModels {
        NoteViewModelFabric(app().noteRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = AppRecyclerAdapter(
            onClickDeleteNote = { note, position ->
                viewModel.delete(note, position)
            },
            onClickMove = { from, to ->
                adapter.move(from, to)
            }
        )
        binding.rvNotes.adapter = adapter

        binding.fabAddNote.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container_view, NoteEditFragment())
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.addToBackStack(null)
                ?.commit()
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.noteListFlow.collect { state ->
                checkListState(state)
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.noteDeleteFlow.collect { state ->
                checkDeleteState(state)
            }
        }

        viewModel.getAllNotes()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun checkListState(state: AppState<List<Note>>?) {
        when(state) {
            is AppState.Success -> {
                binding.notesListProgress.visibility = View.GONE
                state.data?.let { list ->
                    if (list.isEmpty()) {
                        binding.rvNotes.visibility = View.GONE
                        binding.tvNotesEmpty.visibility = View.VISIBLE
                    } else {
                        adapter.setData(list.map { AdapterItem.NoteItem(it) })
                        binding.rvNotes.visibility = View.VISIBLE
                        binding.tvNotesEmpty.visibility = View.GONE
                    }
                }
            }
            is AppState.Error -> {
                binding.rvNotes.visibility = View.GONE
                binding.notesListProgress.visibility = View.GONE
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
            is AppState.Loading -> {
                binding.rvNotes.visibility = View.GONE
                binding.notesListProgress.visibility = View.VISIBLE
            }
        }
    }

    private fun checkDeleteState(state: AppState<Int>?) {
        when(state) {
            is AppState.Success -> {
                binding.notesListProgress.visibility = View.GONE
                if (state.data != null) {
                    adapter.deleteItem(state.data)
                    Toast.makeText(requireContext(), R.string.note_deleted, Toast.LENGTH_SHORT).show()
                    viewModel.itemDeleted()
                }
            }
            is AppState.Error -> {
                binding.notesListProgress.visibility = View.GONE
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
            is AppState.Loading -> {
                binding.notesListProgress.visibility = View.VISIBLE
            }
        }
    }
}