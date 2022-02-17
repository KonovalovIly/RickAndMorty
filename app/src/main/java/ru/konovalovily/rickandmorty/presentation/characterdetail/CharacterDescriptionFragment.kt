package ru.konovalovily.rickandmorty.presentation.characterdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    private fun getCharacterResponse() {
        viewModel.getCharacterDetail(args.characterId)
    }

    private fun observeViewModel() {
        viewModel.characterInfo.observe(this) {
            binding.apply {
                ivCharacterProfile.load(it.image)
                tvCharacterName.text = it.name
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}