package com.example.npod.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import com.example.npod.R
import com.example.npod.data.nasa.pod.PictureState
import com.example.npod.databinding.FragmentMainBinding
import com.example.npod.data.nasa.NasaRepositoryImpl
import com.example.npod.domain.MediaType
import com.example.npod.domain.PictureOfTheDay
import com.example.npod.utils.getFormattedDate
import com.example.npod.ui.ViewModelFactory

class MainFragment : Fragment(R.layout.fragment_main) {
    companion object {
        private const val WIKI_URL = "https://ru.wikipedia.com/wiki/"
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(NasaRepositoryImpl())
    }

    private var isPictureOnFullScreen = false

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

        binding.tilWikiSearch.setEndIconOnClickListener {
            binding.tilWikiSearch.error = null

            val searchQuery = binding.edWikiSearch.text.toString()

            if (searchQuery.isBlank()) {
                binding.tilWikiSearch.error =
                    requireContext().getString(R.string.search_field_is_empty)

                return@setEndIconOnClickListener
            }

            val url = "$WIKI_URL$searchQuery"
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, WebFragment.newInstance(url))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit()
        }

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
                    tvPodLabel.visibility = View.VISIBLE
                    image.visibility = View.VISIBLE
                    image.load(response.url)

                    image.setOnClickListener {
                        isPictureOnFullScreen = isPictureOnFullScreen.not()
                        TransitionManager
                            .beginDelayedTransition(
                                binding.main,
                                TransitionSet()
                                    .addTransition(ChangeBounds())
                                    .addTransition(ChangeImageTransform())
                            )

                        val params: ViewGroup.LayoutParams = binding.image.layoutParams
                        params.height = if (isPictureOnFullScreen) {
                            ViewGroup.LayoutParams.MATCH_PARENT
                        } else {
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        }
                        binding.image.layoutParams = params
                        binding.image.scaleType = if (isPictureOnFullScreen) {
                            ImageView.ScaleType.CENTER_CROP
                        } else {
                            ImageView.ScaleType.FIT_CENTER
                        }
                        TransitionManager.endTransitions(binding.main)
                    }
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