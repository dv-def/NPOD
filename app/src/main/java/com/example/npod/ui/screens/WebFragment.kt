package com.example.npod.ui.screens

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.npod.R
import com.example.npod.databinding.FragmentWebBinding

class WebFragment : Fragment(R.layout.fragment_web) {
    companion object {
        private const val EXTRA_URL_KEY = "EXTRA_URL_KEY"

        fun newInstance(url: String): WebFragment {
            val args = Bundle().apply {
                putString(EXTRA_URL_KEY, url)
            }

            return WebFragment().apply {
                arguments = args
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentWebBinding.bind(view)
        arguments?.let { args ->
            args.getString(EXTRA_URL_KEY)?.let { url ->
                with(binding.webView) {
                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            super.onProgressChanged(view, newProgress)
                            if (newProgress == 100) {
                                binding.progress.visibility = View.GONE
                            }
                        }
                    }

                    webViewClient = object : WebViewClient() {
                        override fun shouldOverrideUrlLoading(view: WebView?, u: String?): Boolean {
                            return false
                        }
                    }

                    settings.javaScriptEnabled = true

                    loadUrl(url)
                }
            }
        }
    }
}