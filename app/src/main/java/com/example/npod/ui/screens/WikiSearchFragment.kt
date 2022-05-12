package com.example.npod.ui.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.npod.R
import com.example.npod.databinding.FragmentWikiSearchBinding

class WikiSearchFragment : Fragment(R.layout.fragment_wiki_search) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentWikiSearchBinding.bind(view)

        binding.btnSearch.setOnClickListener {
            val text = binding.editTextSearch.text.toString()

            if (text.isBlank()) {
                binding.inputLayoutSearch.error =
                    requireContext().getString(R.string.search_field_is_empty)

                return@setOnClickListener
            }

            val url = "https://ru.wikipedia.com/wiki/$text"
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container_view, WebFragment.newInstance(url))
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}