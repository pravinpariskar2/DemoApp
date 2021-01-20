package com.example.shadiapp.dataclass

data class ResponseData(
    var id:Int,
    val info: Info,
    val results: List<Result>
)