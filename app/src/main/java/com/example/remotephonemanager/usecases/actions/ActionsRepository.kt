package com.example.remotephonemanager.usecases.actions

import com.example.remotephonemanager.domain.Action
import com.example.remotephonemanager.usecases.RequestError

interface ActionsRepository {
    fun sendAction(action: Action, sendActionCallback: SendActionCallback)

    interface SendActionCallback {
        fun onSuccess()
        fun onError(requestError: RequestError)
    }

    fun getActions(getActionsCallback: GetActionsCallback)

    interface GetActionsCallback{
        fun onSuccess(actions: List<Action>)
        fun onError(requestError: RequestError)
    }
}
