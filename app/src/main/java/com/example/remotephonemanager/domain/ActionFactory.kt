package com.example.remotephonemanager.domain

import java.util.*

class ActionFactory {
    companion object {
        fun createTakePhotoAction(dstDevice: Device): Action {
            return createDefaultAction(ActionType.TAKE_PHOTO, dstDevice)
        }

        fun createPerformRingAction(dstDevice: Device): Action {
            return createDefaultAction(ActionType.PERFORM_RING, dstDevice)
        }

        fun createLockDeviceAction(dstDevice: Device): Action {
            return createDefaultAction(ActionType.LOCK_DEVICE, dstDevice)
        }

        private fun createDefaultAction(actionType: Int, dstDevice: Device): Action {
            return Action(
                actionType, SessionHolder.session.user,
                SessionHolder.session.device, dstDevice, Date()
            )
        }
    }
}