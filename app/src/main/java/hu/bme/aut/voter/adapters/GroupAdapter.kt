package hu.bme.aut.voter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.voter.R
import hu.bme.aut.voter.databinding.GroupRowBinding
import hu.bme.aut.voter.model.Group

class GroupAdapter(val context: Context) : ListAdapter<Group, GroupAdapter.ViewHolder>(GroupDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = getItem(position)
        holder.bind(group )
    }


    class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = GroupRowBinding.bind(itemView)

        fun bind(item: Group){
            binding.tvGroupName.text = item.name
        }
    }
}

class GroupDiffCallback : DiffUtil.ItemCallback<Group>(){
    override fun areItemsTheSame(oldItem: Group, newItem: Group): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Group, newItem: Group): Boolean {
        return oldItem == newItem
    }
}