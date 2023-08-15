package com.fiveonetwo.particle.domain.scrap

import com.fiveonetwo.particle.domain.parse.HtmlParser
import org.springframework.http.ResponseEntity
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity

class UrlScraper {
    companion object{
        private val webClient: WebClient = WebClient.create()

        private fun readHtml(url: String): String {
            return webClient.get()
                .uri(url)
                .retrieve()
                .toEntity<String>()
                .onErrorReturn(ResponseEntity.ok(""))
                .block()
                ?.body ?: ""
        }

        fun readTitle(url: String): String {
            val html = readHtml(url)
            val parser = HtmlParser(html)
            val results = parser.readTagValues("title")

            return if (results.isEmpty()) "" else results.first()
        }
    }
}