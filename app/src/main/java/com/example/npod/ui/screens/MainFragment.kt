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
import com.example.npod.data.PictureState
import com.example.npod.databinding.FragmentMainBinding
import com.example.npod.data.repository.NasaRepositoryImpl
import com.example.npod.domain.models.MediaType
import com.example.npod.domain.models.PictureOfTheDay
import com.example.npod.utils.getFormattedDate
import com.example.npod.ui.viewmodels.MainViewModel
import com.example.npod.ui.viewmodels.MainViewModelFactory

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
            viewModel.getPictureOfTheDay(getFormattedDate(0))
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.chipBeforeYesterday.setOnClickListener {
            viewModel.getPictureOfTheDay(getFormattedDate(2))
        }

        binding.chipYesterday.setOnClickListener {
            viewModel.getPictureOfTheDay(getFormattedDate(1))
        }

        binding.chipToday.setOnClickListener {
            viewModel.getPictureOfTheDay(getFormattedDate(0))
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.pictureFlow.collect { state ->
                when(state) {
                    is PictureState.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                        binding.image.visibility = View.GONE
                    }
                    is PictureState.Success -> {
                        binding.progress.visibility = View.GONE
                        renderContent(state.response)
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

    private fun renderContent(response: PictureOfTheDay) {
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