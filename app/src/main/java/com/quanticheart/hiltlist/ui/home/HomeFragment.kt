package com.quanticheart.hiltlist.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.quanticheart.hiltlist.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.rlMovies?.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = moviesAdapter
        }

        lifecycleScope.launchWhenCreated {
            viewModel.detailsMovie.collect { list ->
                when (list) {
                    is ScreenState.Success -> moviesAdapter.setData(list.value)
                    is ScreenState.Error -> {}
                    is ScreenState.Loading ->
                        _binding?.flipper?.displayedChild = if (list.value) 0 else 1
                }
            }
        }
        viewModel.load(1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}