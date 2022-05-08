package hu.bme.aut.voter.interfaces

import hu.bme.aut.voter.model.Group
import hu.bme.aut.voter.model.Vote

interface VotesCallback {
    fun votesReadyListener(votes : List<Vote>)
}
interface GroupsCallback{
    fun groupsReadyListener(groups : List<Group>)
}


interface FirebaseResultCallback{
    fun resultListener(success: Boolean, message : String)
}
