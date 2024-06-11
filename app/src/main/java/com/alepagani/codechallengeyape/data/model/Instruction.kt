package com.alepagani.codechallengeyape.data.model

data class Instruction(
    val appliance: String,
    val display_text: String,
    val end_time: Int,
    val id: Int,
    val position: Int,
    val start_time: Int,
    val temperature: Int
)