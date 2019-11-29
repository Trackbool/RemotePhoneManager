package com.example.remotephonemanager.domain.entities

import java.util.*

data class Action(
    var typeId: Int, var userSender: User, var srcDevice: Device,
    var dstDevice: Device, var date: Date
)