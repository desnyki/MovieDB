package com.desnyki.moviedb.movie.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.desnyki.moviedb.App
import com.desnyki.moviedb.R
import com.desnyki.moviedb.data.Result
import com.desnyki.moviedb.databinding.FragmentMovieDetailBinding
import com.desnyki.moviedb.di.injectViewModel
import javax.inject.Inject

class MovieDetailFragment : Fragment() {

    val args: MovieDetailFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MovieViewModel

    override fun onAttach(context: Context) {
        (context.applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = injectViewModel(viewModelFactory)

        val binding = DataBindingUtil.inflate<FragmentMovieDetailBinding>(
            inflater,
            R.layout.fragment_movie_detail, container, false).apply {
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
