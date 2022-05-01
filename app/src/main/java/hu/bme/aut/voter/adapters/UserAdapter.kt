package hu.bme.aut.voter.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.voter.R
import hu.bme.aut.voter.databinding.UserRowBinding
import hu.bme.aut.voter.model.User
import hu.bme.aut.voter.services.UserDatabase

class UserAdapter (val context :Context) : ListAdapter<User, UserAdapter.ViewHolder>(UserDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class ViewHolder(private val itemView : View) : RecyclerView.ViewHolder(itemView){
        private val binding = UserRowBinding.bind(itemView)

        fun bind(item : User){
            binding.tvUsername.text = item.getDisplayName()
            binding.tvEmail.text = item.getEmail()
            binding.ivRemove.setOnClickListener{
                UserDatabase.removeUser(item)
            }
        }

    }
}

class UserDiffCallback : DiffUtil.ItemCallback<User>(){
    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.getDisplayName() == newItem.getDisplayName()
    }

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.getDisplayName() == newItem.getDisplayName()
    }
}