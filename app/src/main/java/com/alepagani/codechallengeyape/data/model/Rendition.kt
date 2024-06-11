package com.alepagani.codechallengeyape.data.model

data class Rendition(
    val aspect: String,
    val bit_rate: Int,
    val container: String,
    val content_type: String,
    val duration: Int,
    val file_size: Int,
    val height: Int,
    val maximum_bit_rate: Int,
    val minimum_bit_rate: Int,
    val name: String,
    val poster_url: String,
    val url: String,
    val width: Int
)