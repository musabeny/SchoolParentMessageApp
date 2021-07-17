package com.beem.schoolmessageapp.model

data class MessageResult(
    val code: Int,
    val duplicates: Int,
    val invalid: Int,
    val message: String,
    val request_id: Int,
    val successful: Boolean,
    val valid: Int
)