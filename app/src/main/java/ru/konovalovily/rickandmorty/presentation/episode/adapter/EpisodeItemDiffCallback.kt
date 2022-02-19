package ru.konovalovily.rickandmorty.presentation.episode.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.konovalovily.rickandmorty.domain.entity.Episode

class EpisodeItemDiffCallback : DiffUtil.ItemCallback<Episode>() {

    override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem == newItem
    }
}