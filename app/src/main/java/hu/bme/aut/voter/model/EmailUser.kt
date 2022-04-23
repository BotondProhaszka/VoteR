package hu.bme.aut.voter.model

import java.io.Serializable

class EmailUser(displayName: String = "", val email: String = "") : User(displayName), Serializable {
    override fun hasRightCreatePoll() = true
}