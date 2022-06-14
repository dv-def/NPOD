package com.example.npod.ui.screens.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.npod.App
import com.example.npod.R
import com.example.npod.data.notes.NoteRepositoryImpl
import com.example.npod.data.notes.NoteState
import com.example.npod.databinding.FragmentNoteEditBinding
import com.example.npod.domain.notes.Note
import com.example.npod.ui.NoteViewModelFabric

class NoteEditFragment : Fragment(R.layout.fragment_note_edit) {
    private var _binding: FragmentNoteEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val viewModel: NoteViewModel by viewModels {
        NoteViewModelFabric(NoteRepositoryImpl(
            (requireContext().applicationContext as App).applicationDatabase.noteDAO())
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.editNoteFlow.collect {
                if (it != null) {
                    collectSaveNote(it)
                }
            }
        }

        initSaveButton()

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun collectSaveNote(noteState: NoteState) {
        when(noteState) {
            is NoteState.Success -> {
                Toast.makeText(requireContext(), R.string.note_saved, Toast.LENGTH_SHORT).show()
            }
            is NoteState.Error -> {
                Toast.makeText(requireContext(), noteState.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initSaveButton() {
        binding.btnSaveNote.setOnClickListener {
            binding.tilEditNoteTitle.error = null
            binding.tilEditNoteDescription.error = null

            val title = binding.edEditNoteTitle.text.toString()
            val description = binding.edEditNoteDescription.text.toString()

            val currentNoteState = CurrentNoteState(
                title = title,
                description = description
            )

            when(val validationResult = viewModel.validate(currentNoteState)) {
                is ValidateNoteResult.Invalid -> {
                    when (validationResult.errorType) {
                        NoteValidateErrorType.TITLE_ERROR -> {
                            binding.tilEditNoteTitle.error = validationResult.message
                        }
                        NoteValidateErrorType.DESCRIPTION_ERROR -> {
                            binding.tilEditNoteDescription.error = validationResult.message
                        }
                    }
                    return@setOnClickListener
                }

                is ValidateNoteResult.Valid -> {
                    viewModel.saveNote(
                        Note(
                            title = title,
                            description = description
                        )
                    )
                }
            }
        }
    }
}