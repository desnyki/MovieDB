package com.desnyki.moviedb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desnyki.moviedb.adapter.MovieListAdapter
import com.desnyki.moviedb.data.Result


class MainFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MovieListAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.main_fragment, container, false)
        viewAdapter = MovieListAdapter(::onClick)
        viewManager = LinearLayoutManager(context)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.list).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.movies.observe(viewLifecycleOwner, Observer { moviesResult ->
            when(moviesResult){
                is Result.Success ->{
                    viewAdapter.moviesList = moviesResult.data
                }
            }
        })

        return rootView
    }

    fun onClick(id:Int){
        val action =
            MainFragmentDirections.actionMainFragmentToMovieDetailFragment(id)
        findNavController().navigate(action)
    }
}
