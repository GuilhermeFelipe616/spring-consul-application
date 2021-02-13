package com.springcloud.consul.controller

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RefreshScope
@RestController
class SpringConsulController(
    @Value("\${consul.example.test.name}")
    private val name: String,
    @Value("\${consul.example.test.nick}")
    private val nick: String,
    @Value("\${consul.example.test.code}")
    private val code: String
) {

    @GetMapping("/spring-consul/name")
    fun getName(): String = name

    @GetMapping("/spring-consul/nick")
    fun getNick(): String = nick

    @GetMapping("/spring-consul/code")
    fun getCode(): String = code

}
