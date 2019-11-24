package com.example.remotephonemanager.domain

import java.util.*

data class Action(
    var typeId: Int, var userSender: User, var srcDevice: Device,
    var dstDevice: Device, var date: Date
)