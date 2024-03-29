package com.example.moviegallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviegallery.data.entity.Movie
import com.example.moviegallery.databinding.ActivityMainBinding
import com.example.moviegallery.utils.DeviceUtils
import com.example.moviegallery.utils.DialogUtils
import com.example.moviegallery.utils.JsonConverter
import com.example.moviegallery.utils.MovieListAdapter
import com.example.moviegallery.utils.SharedPreferenceUtils
import com.example.moviegallery.viewmodels.MovieViewModel
import com.example.moviegallery.views.fragments.ActionResult
import com.example.moviegallery.views.fragments.ActionSearch
import com.example.moviegallery.views.fragments.BaseDialogFragment.DismissListener
import com.example.moviegallery.views.fragments.LoginFragment
import com.example.moviegallery.views.fragments.MovieDetailFragment
import com.example.moviegallery.views.fragments.SearchHistoryFragment

class MainActivity : AppCompatActivity() {

    private lateinit var movieViewModel : MovieViewModel
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        // initialize shared preferences
        SharedPreferenceUtils.init(this)

        // init view model
        movieViewModel = ViewModelProvider.AndroidViewModelFactory(application).create(MovieViewModel::class.java)

        viewModelObservers()

        init()
    }

    override fun onStart() {
        super.onStart()

        // show login
        val loginFragment = LoginFragment()
        loginFragment.setDismissListener(object : DismissListener {
            override fun onDismiss(data: Bundle?) {
                // if user chooses to quit, quit the program
                println(loginFragment.getActionResult().toString())
                if (loginFragment.getActionResult() ==  ActionResult.CANCEL) {
                    finishAffinity()
                    return
                }

                // start populating table
            }
        })
        loginFragment.show(supportFragmentManager, null)
    }

    private fun init() {

        // set title
        binding.header.textviewTitle.text = getString(R.string.app_name_in_app)

        binding.header.buttonLeft.visibility = View.VISIBLE
        binding.header.buttonLeft.setBackgroundResource(R.drawable.selector_history)
        binding.header.buttonLeft.setOnClickListener {
            val searchHistoryFragment = SearchHistoryFragment()
            searchHistoryFragment.show(supportFragmentManager, null)
        }

        binding.buttonClearSearch.setOnClickListener {
            clearSearch()
        }

        binding.header.buttonRight.visibility = View.VISIBLE
        binding.header.buttonRight.setBackgroundResource(R.drawable.selector_exit)
        binding.header.buttonRight.setOnClickListener {
            finishAffinity()
        }

        binding.layoutSearch.buttonSearch.setOnClickListener {

            if (!DeviceUtils.checkInternetConnection(this)) {
                Toast.makeText(this, "Internet connection is not available!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val movieName = binding.layoutSearch.edittextMovieName.text.toString().trim()

            if (movieName.isEmpty()) {
                Toast.makeText(this, "Movie name is empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // show progress dialog
            DialogUtils.setDialog(DialogUtils.showProgressDialog(this, getString(R.string.retrieve_data)))

            movieViewModel.getMoviesFromApi(this, movieName)
        }

        binding.layoutSearch.recyclerViewMovie.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)

        // add divider
        binding.layoutSearch.recyclerViewMovie.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL))
    }

    private fun clearSearch() {
        binding.layoutSearch.edittextMovieName.text.clear()
        reloadAdapter(null)
    }

    private fun reloadAdapter(movieList: List<Movie>?) {

        if (movieList != null) {
            binding.layoutSearch.recyclerViewMovie.adapter= null
            val movieListAdapter = MovieListAdapter(movieList, ActionSearch.INFO) {
                val pos = it.tag as Int
                val movieDetailFragment = MovieDetailFragment(movieViewModel, movieList[pos].movieImdb)
                movieDetailFragment.show(supportFragmentManager, null)
            }
            binding.layoutSearch.recyclerViewMovie.adapter = movieListAdapter
            return
        }

        binding.layoutSearch.recyclerViewMovie.adapter= null
    }

    private fun showSearchResult(show: Boolean) {
        binding.layoutSearch.recyclerViewMovie.visibility = if (show) View.VISIBLE else View.GONE
        binding.layoutSearch.textviewQueryStatus.visibility = if (show) View.GONE else View.VISIBLE
    }

    private fun viewModelObservers() {

        movieViewModel.moviesJsonLiveData.observe(this) {

            // dismiss dialog
            DialogUtils.dismissDialog()

            if (it != null) {

                val response = it.get("Response").asString

                // if found
                if ("True" == response) {

                    // get convert json object to List<Movie>
                    val movieList = JsonConverter.convertToMovieList(it)

                    // insert all searched history into room database
                    movieViewModel.insertMovies(movieList)
                    showSearchResult(true)
                    // reload adapter
                    reloadAdapter(movieList)
                } else {
                    showSearchResult(false)
                    binding.layoutSearch.textviewQueryStatus.text = getString(R.string.movies_not_found)
                }
            } else {
                showSearchResult(false)
                binding.layoutSearch.textviewQueryStatus.text = getString(R.string.unable_to_get_response)
            }
        }
    }
}