package hu.bme.aut.voter.model

import hu.bme.aut.voter.interfaces.UserInterface
import java.io.Serializable

abstract class User (private var displayName: String = "") : Serializable, UserInterface{
    abstract override fun hasRightCreatePoll(): Boolean
    override fun getDisplayName(): String = displayName
}