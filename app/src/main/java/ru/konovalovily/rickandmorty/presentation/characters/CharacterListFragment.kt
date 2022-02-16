package ru.konovalovily.rickandmorty.presentation.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.android.viewmodel.ext.android.viewModel
import ru.konovalovily.rickandmorty.databinding.FragmentCharacterListBinding
import ru.konovalovily.rickandmorty.presentation.adapter.CharacterListAdapter


class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding: FragmentCharacterListBinding
        get() = _binding ?: throw Exception("FragmentCharacterListBinding == null")

    private val viewModel: CharacterListViewModel by viewModel()
    private val characterAdapter: CharacterListAdapter = CharacterListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCharacterListBinding.inflate(inflater, container, false).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCharacterLoading()
        initRecyclerView()
        observeViewModel()
        addClickListener()
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

    private fun initCharacterLoading() {
        viewModel.getCharacters()
    }

    private fun observeViewModel() {
        viewModel.characterList.observe(this) {
            characterAdapter.submitList(it)
        }
    }

    private fun initRecyclerView() {
        binding.rvCharacterList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = characterAdapter
        }
    }
}