package com.desnyki.moviedb.movie.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desnyki.moviedb.App
import com.desnyki.moviedb.R
import com.desnyki.moviedb.data.Result
import com.desnyki.moviedb.di.injectViewModel
import javax.inject.Inject


class MoviesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MovieListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MovieViewModel

    override fun onAttach(context: Context) {
        (context.applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.main_fragment, container, false)
        viewAdapter = MovieListAdapter()
        viewManager = LinearLayoutManager(context)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.list).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewModel = injectViewModel(viewModelFactory)

        viewModel.movies.observe(viewLifecycleOwner, Observer { moviesResult ->
            when(moviesResult){
                is Result.Success ->{
                    viewAdapter.submitList(moviesResult.data)
                }
            }
        })

        return rootView
    }
}
