package hu.bme.aut.voter.model

class GuestUser(displayName: String) : User(displayName) {
    override fun hasRightCreatePoll(): Boolean = false
}