package hu.bme.aut.voter.model

import java.io.Serializable

abstract class User (var displayName: String = "") : Serializable{
    abstract fun hasRightCreatePoll(): Boolean
}