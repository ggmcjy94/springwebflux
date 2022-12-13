package com.example.springwebflux.fn

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class Router {

    @Bean
    fun helloRouter(handler: HelloHandler) : RouterFunction<ServerResponse> =
        route()
            .GET("/hello", handler::sayHello)
            .build()


    @Bean
    fun userRouter (handler: UserHandler) : RouterFunction<ServerResponse> =
        router {
            "/users".nest {
                GET("/{id}", handler::getUser)
                GET("", handler::getAll)
            }
        }
}