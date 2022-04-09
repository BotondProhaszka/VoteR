package hu.bme.aut.voter.model

import android.net.Uri

class LoggedInUser(displayName: String, email: String, picUri: Uri) : User(displayName) {



    override fun hasRightCreatePoll(): Boolean = true

}