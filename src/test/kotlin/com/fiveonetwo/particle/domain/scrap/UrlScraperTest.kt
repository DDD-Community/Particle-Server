package com.fiveonetwo.particle.domain.scrap

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldNotBe

class UrlScraperTest : BehaviorSpec({

    given("url이 주어졌을 때") {
        val url = "https://www.baeldung.com/java-enumset"

        `when`("url scraper를 이용하면") {
            val result = UrlScraper.readTitle(url)

            then("html title 정보가 주어진다.") {
                result shouldNotBe null
            }
        }
    }

//    given("maven url이 주어졌을 때") {
//        val url = "https://mvnrepository.com/artifact/org.jsoup/jsoup/1.16.1"
//        `when`("url scraper를 이용하면") {
//            val result = UrlScraper.readTitle(url)
//
//            then("보안 정책으로 인해서 빈문자열이 반환된다.") {
//                result shouldBe "Maven Repository: org.jsoup » jsoup » 1.16.1"
//            }
//        }
//    }
})