package com.example.npod.ui.screens

import android.graphics.Typeface.BOLD
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
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
import com.example.npod.data.AppState
import com.example.npod.databinding.FragmentMainBinding
import com.example.npod.data.nasa.NasaRepositoryImpl
import com.example.npod.domain.nasa.MediaType
import com.example.npod.domain.nasa.PictureOfTheDay
import com.example.npod.utils.getFormattedDate
import com.example.npod.ui.NasaViewModelFactory

class MainFragment : Fragment(R.layout.fragment_main) {
    companion object {
        private const val WIKI_URL = "https://ru.wikipedia.com/wiki/"
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels {
        NasaViewModelFactory(NasaRepositoryImpl())
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
                    is AppState.Loading -> {
                        binding.progress.visibility = View.VISIBLE
                        binding.image.visibility = View.GONE
                    }
                    is AppState.Success<PictureOfTheDay> -> {
                        binding.progress.visibility = View.GONE
                        state.data?.let { data ->
                            renderContent(data)
                        }
                    }
                    is AppState.Error -> {
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
            val explanation = response.explanation
            val lastIndexOfFirstSentence = getEndOfFirstSentenceIndex(explanation)
            val spannable = SpannableString(explanation).apply {
                setSpan(
                    ForegroundColorSpan(
                        requireContext().getColor(R.color.bottom_sheet_top_line_color)
                    ),
                    0,
                    lastIndexOfFirstSentence,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )

                setSpan(
                    StyleSpan(BOLD),
                    0,
                    lastIndexOfFirstSentence,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
            bottomSheet.bottomSheetExplanation.text = spannable
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

    private fun getEndOfFirstSentenceIndex(text: String): Int {
        val dotIndex = text.indexOf(".")
        val questionIndex = text.indexOf("?")
        val exclamationPointIndex = text.indexOf("!")

        val array = listOf(dotIndex, questionIndex, exclamationPointIndex)
            .filter { it > -1 }

        return if (array.isEmpty()) 0 else array.minOf { it } + 1
    }
}