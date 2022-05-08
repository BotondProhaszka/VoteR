package hu.bme.aut.voter.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import hu.bme.aut.voter.activities.MainActivity
import hu.bme.aut.voter.databinding.DialogCreateGroupBinding
import hu.bme.aut.voter.interfaces.FirebaseResultCallback
import hu.bme.aut.voter.model.Group
import hu.bme.aut.voter.services.GroupDatabase
import kotlin.concurrent.thread

class CreateGroupDialog : DialogFragment(), FirebaseResultCallback {

    private lateinit var binding: DialogCreateGroupBinding
    private lateinit var group: Group

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogCreateGroupBinding.inflate(layoutInflater)

        binding.btnCreate.setOnClickListener {
            createGroup()
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        return binding.root
    }

    private fun createGroup() {
        thread {
            val connectId = MainActivity.user.getEmail().subSequence(0, 4).toString()
                .plus("-" + (100000..999999).random().toString())
            val tempGroup = Group(
                connectId = connectId,
                name = binding.etGroupName.text.toString(),
                ownerEmail = MainActivity.user.getEmail()
            )

            val id = GroupDatabase.getInstance().groupDao().insertGroup(tempGroup)
            group = GroupDatabase.getInstance().groupDao().getGroupById(id)!!
            Log.d(MainActivity.TAG_BUGFIX, "${group.id}, ${group.name}")
            MainActivity.firestoreDatabase.createGroup(group, this)

        }
    }

    override fun resultListener(success: Boolean, message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        if (success) {
            binding.btnCreate.isEnabled = false
            binding.tvConnectID.text = group.connectId
        }
    }
}