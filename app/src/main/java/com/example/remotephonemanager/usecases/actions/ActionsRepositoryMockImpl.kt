package com.example.remotephonemanager.usecases.actions

import com.example.remotephonemanager.domain.entities.Action
import com.example.remotephonemanager.domain.entities.ActionType
import com.example.remotephonemanager.domain.entities.Device
import com.example.remotephonemanager.domain.entities.User
import com.example.remotephonemanager.domain.repository.ActionsRepository
import java.util.*
import kotlin.collections.ArrayList

class ActionsRepositoryMockImpl :
    ActionsRepository {
    override fun sendAction(
        action: Action,
        sendActionCallback: ActionsRepository.SendActionCallback
    ) {
        Thread(Runnable {
            Thread.sleep(2000)
            sendActionCallback.onSuccess()
        }).start()
    }

    override fun getActions(getActionsCallback: ActionsRepository.GetActionsCallback) {
        val actions = ArrayList<Action>()
        actions.add(
            Action(
                ActionType.TAKE_PHOTO,
                User(
                    2,
                    "Mikel1",
                    ""
                ),
                Device(
                    "B001",
                    "Movil Randal",
                    "iPhone X"
                ),
                Device(
                    "A001",
                    "Movil Adri",
                    "Xiaomi Mi4"
                ),
                Date()
            )
        )
        getActionsCallback.onSuccess(actions)
    }
}