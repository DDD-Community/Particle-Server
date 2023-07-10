package com.fiveonetwo.particle.shared.security.provider

import com.fiveonetwo.particle.shared.utils.logger
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class TokenProvider(
        @Value("\${jwt.secret}") private val secretKey: String,
) {
    private val key: Key = Keys.hmacShaKeyFor(secretKey.toByteArray())
    val accessDuration: Long = 1000 * 60 * 60 * 24 * 7
    val refreshDuration: Long = 1000 * 60 * 60 * 24 * 20
    private val logger = logger<TokenProvider>()

    fun createToken(subject: String, duration: Long): String = Jwts.builder().also {
        it.setSubject(subject)
        it.setExpiration(Date(System.currentTimeMillis() + duration))
        it.signWith(key)
    }.compact()

    fun createAccessToken(subject: String): String = createToken(subject, accessDuration)
    fun createRefreshToken(subject: String): String = createToken(subject, refreshDuration)
    fun getSubject(token: String): String {
        val parser = Jwts.parserBuilder().setSigningKey(key).build()
        val jwt = parser.parseClaimsJws(token)
        return jwt.body.subject
    }

    fun getAuthentication(token: String): Authentication {
        val subject = getSubject(token)
        val authentication = UsernamePasswordAuthenticationToken(subject, subject, mutableListOf(SimpleGrantedAuthority("ROLE_USER")))
        return authentication
    }

    fun validateToken(token: String): Boolean = try {
        val parser = Jwts.parserBuilder().setSigningKey(key).build()
        parser.parseClaimsJws(token)
        true
    } catch (e: Exception) {
        logger.info(" token($token) is invalidate")
        false
    }
}