package hu.bme.aut.voter.services

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.voter.activities.MainActivity
import hu.bme.aut.voter.fragments.GroupsFragment
import hu.bme.aut.voter.interfaces.FirebaseResultCallback
import hu.bme.aut.voter.interfaces.GroupsCallback
import hu.bme.aut.voter.interfaces.UserInterface
import hu.bme.aut.voter.interfaces.VotesCallback
import hu.bme.aut.voter.model.Group
import hu.bme.aut.voter.model.Vote

class FirestoreDatabase {
    private val db = Firebase.firestore

    private val VOTES_COLLECTION = "votes"
    private val USERS_COLLECTION = "users"
    private val GROUPS_COLLECTION = "groups"
    private val OWN_GROUPS = "own_groups"
    private val VOTEID = "vote_id"

    fun clearOldVotes() {
        db.collection(VOTES_COLLECTION).document()
    }

    fun createVote(vote: Vote) {
        if (!MainActivity.user.hasRightCreatePoll())
            return
        db.collection(VOTES_COLLECTION).document(VOTEID).set(vote)
    }

    fun getVotes(caller: VotesCallback) {
        val votes = mutableListOf<Vote>()
        db.collection(VOTES_COLLECTION).get()
            .addOnSuccessListener { document ->
                Log.d(MainActivity.TAG_BUGFIX, "#votes : " + document.size().toString())
                for (vote in document)
                    votes.add(vote.toObject(Vote::class.java))
                caller.votesReadyListener(votes)
            }
            .addOnFailureListener { exception ->
                Log.d(MainActivity.TAG_BUGFIX, exception.toString())
            }

    }

    fun createGroup(group: Group, caller: FirebaseResultCallback) {
        db.collection(GROUPS_COLLECTION).document(group.connectId).set(group).addOnSuccessListener {
            caller.resultListener(true, "Group created!")
        }.addOnFailureListener {
            caller.resultListener(false, "Something went wrong!")
        }
    }
    fun deleteGroup(group: Group){
        db.collection(GROUPS_COLLECTION).document(group.connectId).delete()
    }

    fun updateGroup(group: Group){
        db.collection(GROUPS_COLLECTION).document(group.connectId).set(group)
    }

    fun getGroups(caller: GroupsFragment) {
        val groups = mutableListOf<Group>()
        db.collection(GROUPS_COLLECTION).get()
            .addOnSuccessListener { document ->
                Log.d(MainActivity.TAG_BUGFIX, "#allgroups : ${document.size()}")
                for (group in document) {
                    val temp = group.toObject(Group::class.java)
                    if (temp.ownerEmail != MainActivity.user.getEmail())
                        groups.add(temp)
                }
                caller.setAllGroups(groups)
            }
            .addOnFailureListener {
                Log.d(MainActivity.TAG_BUGFIX, it.message.toString())
            }
    }

    fun getGroupsByOwner(ownerEmail: String, caller: GroupsFragment) {
        val groups = mutableListOf<Group>()
        db.collection(GROUPS_COLLECTION).get()
            .addOnSuccessListener { document ->
                Log.d(MainActivity.TAG_BUGFIX, "#owngroups : ${document.size()}")
                for (group in document) {
                    val temp = group.toObject(Group::class.java)
                    if (temp.ownerEmail == ownerEmail)
                        groups.add(temp)
                }
                caller.setOwnGroups(groups)
            }
            .addOnFailureListener {
                Log.d(MainActivity.TAG_BUGFIX, it.message.toString())
            }

    }


    fun addUser(user: UserInterface) {
        db.collection(USERS_COLLECTION).add(user)
    }


}