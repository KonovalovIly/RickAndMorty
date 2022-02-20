package ru.konovalovily.rickandmorty.presentation.episode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel
import ru.konovalovily.rickandmorty.R
import ru.konovalovily.rickandmorty.databinding.FragmentEpisodeListBinding
import ru.konovalovily.rickandmorty.presentation.episode.adapter.EpisodeListAdapter

class EpisodeListFragment : Fragment() {

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding: FragmentEpisodeListBinding
        get() = _binding ?: throw Exception("FragmentEpisodeListBinding == null")

    private val args by navArgs<EpisodeListFragmentArgs>()
    private val viewModel by viewModel<EpisodeListViewModel>()
    private val episodeAdapter = EpisodeListAdapter()

    private val snackbar: Snackbar by lazy {
        Snackbar.make(
            binding.root,
            R.string.loading_error,
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction(R.string.ok) { requireActivity().onBackPressed() }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentEpisodeListBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFilteredEpisodeList()
        initRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.episodeList.collectLatest {
                episodeAdapter.submitData(it)
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            binding.errorMessage.text = it.message
        }
    }

    private fun initRecyclerView() {
        binding.rvEpisodeList.adapter = episodeAdapter
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            episodeAdapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> binding.pbCharacterLoading.visibility = View.VISIBLE
                    is LoadState.NotLoading -> binding.pbCharacterLoading.visibility = View.GONE
                    is LoadState.Error -> snackbar.show()
                }
            }
        }
    }

    private fun getFilteredEpisodeList() {
        viewModel.filterEpisodesList(args.episodesList?.toList())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }

}