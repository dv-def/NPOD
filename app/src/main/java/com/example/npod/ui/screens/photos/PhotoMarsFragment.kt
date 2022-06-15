package com.example.npod.ui.screens.photos

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.npod.R
import com.example.npod.data.AppState
import com.example.npod.data.nasa.NasaRepositoryImpl
import com.example.npod.databinding.FragmentPhotoMarsBinding
import com.example.npod.ui.NasaViewModelFactory
import com.example.npod.utils.getFormattedDate

class PhotoMarsFragment : Fragment(R.layout.fragment_photo_mars) {
    private val viewModel: PhotosViewModel by viewModels {
        NasaViewModelFactory(NasaRepositoryImpl())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentPhotoMarsBinding.bind(view)

        val photoAdapter = PhotosMarsAdapter()
        binding.rvPhotos.adapter = photoAdapter

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.photosFlow.collect { state ->
                when (state) {
                    is AppState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        state.data?.let {
                            photoAdapter.setPhotos(state.data)
                            photoAdapter.notifyDataSetChanged()
                        }
                    }

                    is AppState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }

                    is AppState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModel.getMarsPhotos(getFormattedDate(30))
    }
}