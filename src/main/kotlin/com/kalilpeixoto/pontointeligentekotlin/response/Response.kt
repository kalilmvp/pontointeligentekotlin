package com.kalilpeixoto.pontointeligentekotlin.response

data class Response<T>(val erros: ArrayList<String> = arrayListOf(),
                       val data: T? = null)