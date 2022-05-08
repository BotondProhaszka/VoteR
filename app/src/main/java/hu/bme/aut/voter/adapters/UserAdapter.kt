package hu.bme.aut.voter.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.voter.R
import hu.bme.aut.voter.databinding.UserRowBinding
import hu.bme.aut.voter.interfaces.RemoveUser
import hu.bme.aut.voter.interfaces.UserInterface
import hu.bme.aut.voter.services.UserDatabase
import java.util.function.BinaryOperator

class UserAdapter(val context: Context, private val isOwnGroup: Boolean, private val caller: RemoveUser) :
    ListAdapter<UserInterface, UserAdapter.ViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user, context, isOwnGroup, caller)
    }

    class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = UserRowBinding.bind(itemView)

        fun bind(item: UserInterface, context: Context, isOwnGroup: Boolean, caller: RemoveUser) {
            binding.tvUsername.text = item.getDisplayName()
            binding.tvEmail.text = item.getEmail()
            if (isOwnGroup) {
                binding.ivRemove.setOnClickListener {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Remove member")
                    builder.setMessage("Are you sure you want to remove?")
                    builder.setIcon(android.R.drawable.ic_dialog_alert)
                    builder.setPositiveButton("Yes") { dialogInterface, which ->
                        caller.removeUser(item)
                    }
                    builder.setNegativeButton("Cancel"){ dialogInterface, which -> }
                }
            }
        }

    }
}

class UserDiffCallback : DiffUtil.ItemCallback<UserInterface>() {
    override fun areContentsTheSame(oldItem: UserInterface, newItem: UserInterface): Boolean {
        return oldItem.getDisplayName() == newItem.getDisplayName()
    }

    override fun areItemsTheSame(oldItem: UserInterface, newItem: UserInterface): Boolean {
        return oldItem.getDisplayName() == newItem.getDisplayName()
    }
}