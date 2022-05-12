package com.example.npod.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import coil.load
import com.example.npod.R
import com.example.npod.api.MediaType
import com.example.npod.api.PictureOfTheDayResponse
import com.example.npod.api.PictureState
import com.example.npod.databinding.FragmentMainBinding
import com.example.npod.domain.NasaRepositoryImpl
import com.example.npod.viewmodels.MainViewModel
import com.example.npod.viewmodels.MainViewModelFactory

class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(NasaRepositoryImpl())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        if (savedInstanceState == null) {
            viewModel.getPictureOfTheDay()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.pictureFlow.collect { state ->
                when(state) {
                    is PictureState.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                    }
                    is PictureState.Success -> {
                        binding.progress.visibility = View.GONE
                        renderContent(state.response)
                        binding.image.load(state.response.url)
                    }
                    is PictureState.Error -> {
                        binding.progress.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderContent(response: PictureOfTheDayResponse) {
        with(binding) {
            when (response.mediaType) {
                MediaType.IMAGE.value -> {
                    image.visibility = View.VISIBLE
                    image.load(response.url)
                }
                MediaType.VIDEO.value -> {
                    videoContentLabel.visibility = View.VISIBLE
                    btnVideoWatch.visibility = View.VISIBLE

                    btnVideoWatch.setOnClickListener {
                        openWebFragment(response.url)
                    }
                }
            }
            bottomSheet.bottomSheetTitle.text = response.title
            bottomSheet.bottomSheetExplanation.text = response.explanation
        }
    }

    private fun openWebFragment(url: String) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(
                R.id.fragment_container_view,
                WebFragment.newInstance(url),
                "WebFragment"
            )
            ?.addToBackStack(null)
            ?.commit()
    }
}