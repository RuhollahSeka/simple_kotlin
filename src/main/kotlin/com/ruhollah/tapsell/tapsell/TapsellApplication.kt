package com.ruhollah.tapsell.tapsell

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TapsellApplication

fun main(args: Array<String>)
{
    runApplication<TapsellApplication>(*args)
}