package com.example.roblemejorado.model

class Chat {
    var dateTime: String? = null
    var textMessage: String? = null
    var sender: String? = null
    var receiver: String? = null

    constructor() {}
    constructor(
        dateTime: String?,
        textMessage: String?,
        sender: String?,
        receiver: String?
    ) {
        this.dateTime = dateTime
        this.textMessage = textMessage
        this.sender = sender
        this.receiver = receiver
    }
}
