package hu.bme.aut.voter.services

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.voter.activities.MainActivity
import hu.bme.aut.voter.interfaces.GroupsCallback
import hu.bme.aut.voter.interfaces.VotesCallback
import hu.bme.aut.voter.model.User
import hu.bme.aut.voter.model.Vote

class FirestoreDatabase {
    private val db = Firebase.firestore

    private val VOTES_COLLECTION = "votes"
    private val VOTEID = "vote_id"
    private val USERS_COLLECTION = "users"

    fun clearOldVotes() {
        db.collection(VOTES_COLLECTION).document()
    }

    fun createVote(vote: Vote) {
        if (!MainActivity.user.hasRightCreatePoll())
            return
        db.collection(VOTES_COLLECTION).document(VOTEID).set(vote)
    }

    fun getVotes(caller : VotesCallback) {
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

    fun getGroups(caller : GroupsCallback){

    }

    fun addUser(user: User){
        db.collection(USERS_COLLECTION).add(user)
    }

}