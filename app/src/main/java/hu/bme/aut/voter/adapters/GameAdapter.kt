package hu.bme.aut.voter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.voter.R
import hu.bme.aut.voter.data.Game
import hu.bme.aut.voter.data.GameDatabase
import hu.bme.aut.voter.databinding.GameRowBinding
import kotlin.concurrent.thread

class GameAdapter(val context: Context) : ListAdapter<Game, GameAdapter.ViewHolder>(GameDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = getItem(position)
        holder.bind(game)
    }

    class ViewHolder(private val itemView: View,) : RecyclerView.ViewHolder(itemView) {
        private val binding = GameRowBinding.bind(itemView)

        fun bind(item : Game) {
            binding.tvGameName.text = item.gameName
            binding.cbIsSelected.isChecked = item.gameIsSelected
            //binding.ivIcon.setImageIcon(R.drawable.)
            binding.cbIsSelected.setOnClickListener {
                item.gameIsSelected = binding.cbIsSelected.isChecked
                thread {
                    GameDatabase.getInstance().gameDao().update(item)
                }
            }
            binding.ivDelete.setOnClickListener{
                thread {
                    GameDatabase.getInstance().gameDao().deleteGame(item)
                }
            }
        }

    }

}
class GameDiffCallback : DiffUtil.ItemCallback<Game>() {
    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.gameId == newItem.gameId
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }
}