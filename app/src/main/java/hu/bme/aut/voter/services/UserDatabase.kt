package hu.bme.aut.voter.services

import hu.bme.aut.voter.interfaces.UserInterface


class UserDatabase {
    companion object {
        private val users: MutableList<UserInterface> = mutableListOf()

        fun initDatabase() {
            users.clear()
        }

        fun getUsers() : List<UserInterface> {
            return users.toList()
        }

        fun addUser(user: UserInterface) {
            users.add(user)
        }

        fun removeUser(user: UserInterface) {
            users.remove(user)
        }
    }
}