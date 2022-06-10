package com.example.npod.ui.screens.photos

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.example.npod.R
import com.example.npod.data.PhotoState
import com.example.npod.data.NasaRepositoryImpl
import com.example.npod.databinding.FragmentPhotoMarsBinding
import com.example.npod.ui.screens.photos.adapter.PhotosMarsAdapter
import com.example.npod.ui.ViewModelFactory
import com.example.npod.utils.getFormattedDate

class PhotoMarsFragment : Fragment(R.layout.fragment_photo_mars) {
    private val viewModel: PhotosViewModel by viewModels {
        ViewModelFactory(NasaRepositoryImpl())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentPhotoMarsBinding.bind(view)

        val photoAdapter = PhotosMarsAdapter()
        binding.rvPhotos.adapter = photoAdapter

        viewLifecycleOwner.lifecycle.coroutineScope.launchWhenStarted {
            viewModel.photosFlow.collect { state ->
                when (state) {
                    is PhotoState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        photoAdapter.setPhotos(state.photos)
                        photoAdapter.notifyDataSetChanged()
                    }

                    is PhotoState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                    }

                    is PhotoState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModel.getMarsPhotos(getFormattedDate(30))
    }
}