package hu.bme.aut.voter.interfaces

import hu.bme.aut.voter.model.Vote

interface FirebaseCallback {
    fun votesReadyListener(votes : List<Vote>)
}