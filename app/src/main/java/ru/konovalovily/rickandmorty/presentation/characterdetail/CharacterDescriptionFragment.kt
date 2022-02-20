package ru.konovalovily.rickandmorty.presentation.characterdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import org.koin.android.viewmodel.ext.android.viewModel
import ru.konovalovily.rickandmorty.databinding.FragmentCharacterDescriptionBinding


class CharacterDescriptionFragment : Fragment() {

    private var _binding: FragmentCharacterDescriptionBinding? = null
    private val binding: FragmentCharacterDescriptionBinding
        get() = _binding ?: throw Exception("FragmentCharacterDescriptionBinding == null")

    private val args by navArgs<CharacterDescriptionFragmentArgs>()

    private val viewModel: CharacterDescriptionViewModel by viewModel()
    private var episodesList: Array<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCharacterDescriptionBinding.inflate(inflater, container, false)
        .apply {
            _binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCharacterResponse()
        observeViewModel()
        setListeners()
    }

    private fun setListeners() {
        binding.buttonEpisodes.setOnClickListener {
            findNavController().navigate(
                CharacterDescriptionFragmentDirections
                    .actionCharacterDescriptionFragmentToEpisodeListFragment(episodesList)
            )
        }
    }

    private fun getCharacterResponse() {
        viewModel.getCharacterDetail(args.characterId)
    }

    private fun observeViewModel() {
        viewModel.characterInfo.observe(viewLifecycleOwner) {
            binding.apply {
                ivCharacterProfile.load(it.image)
                tvCharacterName.text = it.name
                tvCharacterFragmentStatus.text = it.status
                tvCharacterFragmentGender.text = it.gender
                tvCharacterFragmentSpecies.text = it.species
                tvCharacterFragmentLocation.text = it.location.name
                viewModel.endLoading()
                episodesList = it.episode.toTypedArray()
            }
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            loading(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            binding.errorMessage.visibility = View.VISIBLE
            binding.pbCharacterDetailLoading.visibility = View.INVISIBLE
        }
    }

    private fun loading(state: Boolean) {
        if (state) binding.pbCharacterDetailLoading.visibility = View.VISIBLE
        else {
            binding.pbCharacterDetailLoading.visibility = View.INVISIBLE
            binding.characterCardView.visibility = View.VISIBLE
            binding.characterInfoCardView.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}