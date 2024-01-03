package com.aga.data.data.model

class DefaultListResponse<T>(
    val code: Int,
    val message: String,
    val dataList: List<T>?
)