package ru.konovalovily.rickandmorty.presentation.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.viewmodel.ext.android.viewModel
import ru.konovalovily.rickandmorty.R
import ru.konovalovily.rickandmorty.databinding.FragmentCharacterListBinding
import ru.konovalovily.rickandmorty.presentation.characters.adapter.CharacterListAdapter


class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding: FragmentCharacterListBinding
        get() = _binding ?: throw Exception("FragmentCharacterListBinding == null")

    private val viewModel: CharacterListViewModel by viewModel()
    private val characterAdapter: CharacterListAdapter = CharacterListAdapter()

    private val snackbar: Snackbar by lazy {
        Snackbar.make(
            binding.root,
            R.string.loading_error,
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction(R.string.ok) { requireActivity().finish() }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCharacterListBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        addClickListener()
        observeViewModel()
    }

    private fun addClickListener() {
        characterAdapter.onClick = {
            startDescriptionFragment(it)
        }
    }

    private fun startDescriptionFragment(characterId: Int) {
        findNavController().navigate(
            CharacterListFragmentDirections
                .actionCharacterListFragmentToCharacterDescriptionFragment(
                    characterId
                )
        )
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.characterList.collectLatest {
                characterAdapter.submitData(it)
            }
        }
        viewModel.error.observe(this) {
            binding.errorMessage.text = it.message
        }
    }

    private fun initRecyclerView() {
        binding.rvCharacterList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = characterAdapter
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            characterAdapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.Loading -> binding.pbCharacterLoading.visibility = View.VISIBLE
                    is LoadState.NotLoading -> binding.pbCharacterLoading.visibility = View.GONE
                    is LoadState.Error -> snackbar.show()
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}