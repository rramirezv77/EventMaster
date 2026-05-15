package com.rodrigo.eventmaster.model

data class Event(
    val id: Long = 0,
    val title: String,
    val description: String,
    val categoryId: Long,
    val categoryName: String
)
