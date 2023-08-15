package com.fiveonetwo.particle.domain.parse

class HtmlParser(
    private val html: String
) {
    fun readTagValues(tagName: String): List<String> {
        val prefix = "<$tagName>"  // ex) <title>
        val postfix = "</$tagName>"// ex) </title>

        val regex = "${prefix}.*${postfix}".toRegex() // ex) <title>.*</title>
        return regex.findAll(html)
            .map { it.value }
            .map { raw -> raw.substring(prefix.length, raw.length - postfix.length) }
            .toList()
    }
}