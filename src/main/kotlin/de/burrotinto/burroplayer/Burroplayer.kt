package de.burrotinto.burroplayer

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class Burroplayer

fun main(args: Array<String>) {
    SpringApplication.run(Burroplayer::class.java, *args)
}