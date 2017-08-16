package org.humeurvagabonde.playtime

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class PlaytimeApplication

fun main(args: Array<String>) {
    SpringApplication.run(PlaytimeApplication::class.java, *args)
}
