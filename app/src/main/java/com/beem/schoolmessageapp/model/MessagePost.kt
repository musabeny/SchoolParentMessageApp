package com.beem.schoolmessageapp.model

data class MessagePost(
    val encoding: String,
    val message: String,
    val recipients: List<Recipient>,
    val schedule_time: String,
    val source_addr: String
)