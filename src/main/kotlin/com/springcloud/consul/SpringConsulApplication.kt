package com.springcloud.consul

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient
class SpringConsulApplication

fun main(args: Array<String>) {
	runApplication<SpringConsulApplication>(*args)
}
