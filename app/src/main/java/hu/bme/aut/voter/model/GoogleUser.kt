package hu.bme.aut.voter.model

import android.net.Uri
import java.io.Serializable

class GoogleUser(displayName: String = "", val email: String = "", val picUri: String = "") : User(displayName), Serializable {
    override fun hasRightCreatePoll(): Boolean = true

}