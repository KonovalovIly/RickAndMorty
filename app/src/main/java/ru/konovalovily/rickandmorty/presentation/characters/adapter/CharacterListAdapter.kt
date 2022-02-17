package ru.konovalovily.rickandmorty.presentation.characters.adapter

import android.view.LayoutInflater
import coil.load
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import ru.konovalovily.rickandmorty.databinding.CharacterItemBinding
import ru.konovalovily.rickandmorty.domain.entity.Character

class CharacterListAdapter :
    PagingDataAdapter<Character, CharacterListAdapter.ViewHolder>(CharacterItemDiffCallback()) {

    var onClick: ((characterId: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(binding: CharacterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val ivCharacter: ImageView = binding.ivCharacter
        private val tvNameCharacter: TextView = binding.tvNameCharacter
        private val cardView: MaterialCardView = binding.cardView

        fun bind(item: Character) {
            tvNameCharacter.text = item.name
            ivCharacter.load(item.image)
            cardView.setOnClickListener {
                onClick?.invoke(item.id)
            }
        }
    }
}