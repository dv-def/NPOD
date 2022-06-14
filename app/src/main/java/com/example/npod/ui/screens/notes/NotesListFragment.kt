package com.example.npod.ui.screens.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.npod.R
import com.example.npod.databinding.FragmentNotesListBinding

class NotesListFragment : Fragment(R.layout.fragment_notes_list) {
    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fabAddNote.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container_view, NoteEditFragment())
                ?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}