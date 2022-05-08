package hu.bme.aut.voter.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.voter.R
import hu.bme.aut.voter.databinding.GroupRowBinding
import hu.bme.aut.voter.dialog.OwnGroupDialog
import hu.bme.aut.voter.model.Group

class GroupAdapter(val context: FragmentActivity, private val isOwnGroup : Boolean) : ListAdapter<Group, GroupAdapter.ViewHolder>(GroupDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val group = getItem(position)
        holder.bind(group, context, isOwnGroup)
    }


    class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = GroupRowBinding.bind(itemView)

        fun bind(item: Group, context: FragmentActivity, isOwnGroup: Boolean){
            binding.tvGroupName.text = item.name
            binding.root.setOnClickListener{
                if(isOwnGroup){
                    val ownGroupDialog = OwnGroupDialog(item)
                    val transaction = context.supportFragmentManager.beginTransaction()
                    ownGroupDialog.show(transaction, "Own group")
                }
            }
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