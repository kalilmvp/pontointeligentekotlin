package com.kalilpeixoto.pontointeligentekotlin

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class PontointeligentekotlinApplication

fun main(args: Array<String>) {
    SpringApplication.run(PontointeligentekotlinApplication::class.java, *args)
}

fun algumaCoisa() {
    println("testando")
}