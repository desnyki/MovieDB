package com.desnyki.moviedb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.desnyki.moviedb.data.Result
import com.desnyki.moviedb.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {

    val args: MovieDetailFragmentArgs by navArgs()

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentMovieDetailBinding>(
            inflater, R.layout.fragment_movie_detail, container, false).apply {
            lifecycleOwner = this@MovieDetailFragment
        }

        viewModel.observeMovie(args.id).observe(viewLifecycleOwner, Observer {
                movieResult ->
                    when(movieResult){
                        is Result.Success ->{
                            binding.apply {
                                movie = movieResult.data
                            }
                        }
                    }
        })

        return binding.root
    }
}
