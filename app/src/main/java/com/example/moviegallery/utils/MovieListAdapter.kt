package com.example.moviegallery.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.moviegallery.R
import com.example.moviegallery.data.entity.Movie
import com.example.moviegallery.views.fragments.ActionSearch

class MovieListAdapter(private val movieList: List<Movie>,
                       private val action: ActionSearch,
                       private val onClickListener: View.OnClickListener) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //val body : ConstraintLayout
        val body : ConstraintLayout

        init {
            //body = view.findViewById(R.id.body)
            body = view.findViewById(R.id.body)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.layout_movie_list_container, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // get movie from position
        val movie = movieList[position]

        // populate all info into body
        val body = viewHolder.body
        val textviewNumber = body.findViewById<TextView>(R.id.textview_number)
        val textviewMovieName = body.findViewById<TextView>(R.id.textview_movie_name)
        val textviewMovieType = body.findViewById<TextView>(R.id.textview_movie_type)
        val textviewMovieYear = body.findViewById<TextView>(R.id.textview_movie_year)
        val buttonMovieInfo =  body.findViewById<TextView>(R.id.button_movie_info)
        buttonMovieInfo.tag = position

        textviewNumber.text = position.toString()
        textviewMovieName.text = movie.movieName
        textviewMovieYear.text = "Year: ".plus(movie.movieYear)
        textviewMovieType.text = "Type: ".plus(movie.movieType)

        val drawable = if (action == ActionSearch.INFO) R.drawable.selector_movie_info else R.drawable.selector_delete
        buttonMovieInfo.setBackgroundResource(drawable)

        buttonMovieInfo.setOnClickListener(onClickListener)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}