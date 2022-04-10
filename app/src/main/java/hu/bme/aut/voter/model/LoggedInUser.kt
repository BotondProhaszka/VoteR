package hu.bme.aut.voter.model

import android.net.Uri

class LoggedInUser(displayName: String, email: String, val picUri: String) : User(displayName, email) {



    override fun hasRightCreatePoll(): Boolean = true

}