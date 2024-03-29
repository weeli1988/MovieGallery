package com.example.moviegallery.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviegallery.MainActivity
import com.example.moviegallery.R
import com.example.moviegallery.data.entity.Movie
import com.example.moviegallery.databinding.FragmentSearchHistoryBinding
import com.example.moviegallery.utils.DialogUtils
import com.example.moviegallery.utils.MovieListAdapter
import com.example.moviegallery.viewmodels.MovieViewModel

class SearchHistoryFragment : BaseDialogFragment() {

    private lateinit var binding : FragmentSearchHistoryBinding
    private lateinit var movieViewModel : MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentSearchHistoryBinding.inflate(inflater, container, false)

        val view = binding.root

        // init view model
        movieViewModel = ViewModelProvider.AndroidViewModelFactory((context as MainActivity).application).create(MovieViewModel::class.java)

        binding.header.textviewTitle.text = getString(R.string.search_history)

        binding.header.buttonLeft.setOnClickListener {
            setActionResult(ActionResult.OK)
            dismiss()
        }

        viewModelObservers()

        init()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {

        binding.buttonClearSearch.setOnClickListener {
            clearSearch()
        }

        binding.layoutSearch.buttonSearch.setOnClickListener {

            // show progress dialog
            DialogUtils.setDialog(DialogUtils.showProgressDialog(requireContext(), getString(R.string.retrieve_data)))

            val movieName = binding.layoutSearch.edittextMovieName.text.toString().trim()

            if (movieName.isEmpty()) {
                movieViewModel.getMoviesFromSearchHistoryOnSync()
                return@setOnClickListener
            }

            movieViewModel.getMoviesByNameOnSync(movieName)
        }

        binding.layoutSearch.recyclerViewMovie.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL ,false)

        // add divider
        binding.layoutSearch.recyclerViewMovie.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.HORIZONTAL))
    }

    private fun clearSearch() {
        binding.layoutSearch.edittextMovieName.text.clear()
        reloadAdapter(null)
    }

    private fun reloadAdapter(movieList: List<Movie>?) {

        val show = movieList.isNullOrEmpty()

        if (movieList != null) {
            binding.layoutSearch.recyclerViewMovie.adapter= null
            val movieListAdapter = MovieListAdapter(movieList, ActionSearch.DELETE) {

                DialogUtils.setDialog(DialogUtils.showProgressDialog(requireContext(), getString(R.string.reload_data)))
                val pos = it.tag as Int
                movieViewModel.deleteMovie(movieList[pos])
            }
            binding.layoutSearch.recyclerViewMovie.adapter = movieListAdapter
            return
        }

        binding.layoutSearch.recyclerViewMovie.adapter= null

        showSearchResult(show)
    }

    private fun showSearchResult(show: Boolean) {
        binding.layoutSearch.recyclerViewMovie.visibility = if (show) View.VISIBLE else View.GONE
        binding.layoutSearch.textviewQueryStatus.visibility = if (show) View.GONE else View.VISIBLE
        binding.layoutSearch.textviewQueryStatus.text = if (show) "" else getString(R.string.movies_not_found)
    }

    private fun viewModelObservers() {

        movieViewModel.moviesRetrieveLiveData.observe(viewLifecycleOwner) {

            // dismiss dialog
            DialogUtils.dismissDialog()

            if (it != null && it.isNotEmpty()) {

                showSearchResult(true)
                // reload adapter
                reloadAdapter(it)
            } else {
                showSearchResult(false)
                binding.layoutSearch.textviewQueryStatus.text = getString(R.string.movies_not_found)
            }
        }

        movieViewModel.movieDeleteLiveData.observe(viewLifecycleOwner) {

            DialogUtils.dismissDialog()

            val name = binding.layoutSearch.edittextMovieName.text.toString().trim()

            if (name.isNotEmpty()) {
                movieViewModel.getMoviesFromSearchHistoryOnSync()
                return@observe
            }

            movieViewModel.getMoviesByNameOnSync(binding.layoutSearch.edittextMovieName.text.toString().trim())
        }
    }
}