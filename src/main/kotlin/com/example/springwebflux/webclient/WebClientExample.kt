package com.example.springwebflux.webclient

import com.example.springwebflux.book.Book
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@RestController
class WebClientExample {

    val url = "http://localhost:8080/books"

    val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/books/block")
    fun getBooksBlockingWay() : List<Book> {
        log.info("start RestTemplate")

        val restTemplate = RestTemplate()
        val response = restTemplate.exchange(url, HttpMethod.GET, null,
            object : ParameterizedTypeReference<List<Book>>() {})

        val result = response.body!!
        log.info("result: {}", result)
        log.info("Finish RestTemplate")
        return result
    }

    @GetMapping("/books/nonblock")
    fun getBooksNonBLockingWay() : Flux<Book> {
        log.info("Start webclient")
        val flux = WebClient.create()
            .get()
            .uri(url)
            .retrieve()
            .bodyToFlux(Book::class.java)
            .map {
                log.info("resutl: {}", it)
                it
            }
        log.info("finish webclient")
        return flux
    }


}