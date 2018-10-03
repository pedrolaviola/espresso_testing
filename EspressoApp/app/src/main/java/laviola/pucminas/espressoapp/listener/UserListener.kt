package laviola.pucminas.espressoapp.listener

import laviola.pucminas.espressoapp.model.User

interface UserListener {
    fun onUserClick(user: User)
}