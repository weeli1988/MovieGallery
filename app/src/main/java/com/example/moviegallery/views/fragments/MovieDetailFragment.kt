package com.example.moviegallery.views.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.moviegallery.R
import com.example.moviegallery.databinding.FragmentMovieDetailBinding
import com.example.moviegallery.utils.DialogUtils
import com.example.moviegallery.utils.JsonConverter
import com.example.moviegallery.viewmodels.MovieViewModel

class MovieDetailFragment(private val movieViewModel: MovieViewModel, private val movieImdb: String) : BaseDialogFragment() {

    private lateinit var binding : FragmentMovieDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentMovieDetailBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        binding.header.textviewTitle.text = getString(R.string.movie_detail)
        binding.header.buttonLeft.setOnClickListener {
            dismiss()
        }

        binding.imageviewMoviePoster.setBackgroundResource(0)
        binding.textviewMovieContent.text = ""

        // init observer
        viewModelObservers()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // show progress dialog

        Handler(Looper.myLooper()!!).post {
            DialogUtils.setDialog(DialogUtils.showProgressDialog(requireContext(), getString(R.string.retrieve_data)))
        }

        movieViewModel.getMovieDetailFromApi(requireContext(), movieImdb)
    }

    private fun viewModelObservers() {

        movieViewModel.movieDetailLiveData.observe(viewLifecycleOwner) {

            if (it != null) {
                // convert json object to string format and remove all special chars
                val poster = it.get("Poster").asString

                // show picture
                try {
                    Glide.with(this).load(poster).into(binding.imageviewMoviePoster)
                } catch (_: Exception) {

                }

                val content = JsonConverter.convertToString(it)
                binding.textviewMovieContent.text = content
            } else {
                binding.textviewMovieContent.text = getString(R.string.unable_to_get_response)
            }

            // dismiss dialog
            DialogUtils.dismissDialog()
        }
    }
}