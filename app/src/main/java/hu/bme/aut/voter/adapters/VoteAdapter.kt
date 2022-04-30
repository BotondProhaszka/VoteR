package hu.bme.aut.voter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.voter.R
import hu.bme.aut.voter.activities.MainActivity
import hu.bme.aut.voter.databinding.VoteRowBinding
import hu.bme.aut.voter.model.Vote

class VoteAdapter(val context: Context) : ListAdapter<Vote, VoteAdapter.ViewHolder>(VoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoteAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_row, parent, false)
        return VoteAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = getItem(position)
        holder.bind(game)
    }

    class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = VoteRowBinding.bind(itemView)

        fun bind(item: Vote) {
            binding.tvVoteName.text = item.name
            binding.tvDeadline.text = item.deadline.toString()
            binding.tvRemains.text = MainActivity.dateTimeService.getRemainingTime(
                MainActivity.dateTimeService.getCurrentUTCTime(),
                MainActivity.dateTimeService.convertStringToTime(item.deadline)
            ).toString().plus(" min")
        }
    }
}

class VoteDiffCallback : DiffUtil.ItemCallback<Vote>(){
    override fun areItemsTheSame(oldItem: Vote, newItem: Vote): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Vote, newItem: Vote): Boolean {
        return oldItem == newItem
    }
}