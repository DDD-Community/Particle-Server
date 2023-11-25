package com.fiveonetwo.particle.domain.user.entity

import com.fiveonetwo.particle.domain.common.entity.BaseTimeEntity
import com.fiveonetwo.particle.domain.record.entity.Tag
import com.fiveonetwo.particle.domain.user.entity.UsernameContext.Companion.Color
import com.fiveonetwo.particle.domain.user.entity.UsernameContext.Companion.Figure
import com.fiveonetwo.particle.shared.utils.uuid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import kotlin.random.Random
import kotlin.random.nextInt

@Table(name = "users")
@Entity
class User(
    @Id
    @Column(name = "user_id", nullable = false)
    val id: String = uuid(),
    @Column(name = "nickname", nullable = false, columnDefinition = "text")
    val nickname: String = createRandomUsername(),
    @Column(name = "provider", nullable = false)
    val provider: String,
    @Column(name = "identifier", nullable = false, columnDefinition = "text")
    val identifier: String,
    @Column(name = "profile_image_url", columnDefinition = "text")
    val profileImageUrl: String = findUserProfileImageByNickname(nickname),
    @Column(name = "interested_tags", nullable = false, columnDefinition = "varbinary(255)")
    val interestedTags: MutableList<Tag> = mutableListOf(),
) : BaseTimeEntity()

fun createRandomUsername(): String {
    val colors = Color.values()
    val figures = Figure.values()
    return "${colors[Random.nextInt(0..colors.lastIndex)].value} ${figures[Random.nextInt(0..figures.lastIndex)].value}"
}

fun findUserProfileImageByNickname(nickname: String): String {
    val (first, second) = nickname.split(" ")

    val color = Color.originValueOf(first)
    val figure = Figure.originValueOf(second)

    return "https://ddd-particle-bucket.s3.ap-northeast-2.amazonaws.com/profile/${figure.name.lowercase()}_${color.name.lowercase()}.png"
}

class UsernameContext {
    companion object {
        enum class Color(val value: String) {
            RED("빨간"),
            ORANGE("주황"),
            YELLOW("노랑"),
            GREEN("초록"),
            BLUE("파란"),
            PURPLE("보라"),
            PICK("분홍");

            companion object {
                fun originValueOf(value: String): Color {
                    return values().firstOrNull { color -> color.value == value }
                        ?: throw IllegalArgumentException("해당하는 color tag가 없습니다.")
                }
            }
        }

        enum class Figure(val value: String) {
            RECTANGLE("직사각형"),
            TRIANGLE("세모"),
            SQUARE("네모"),
            CIRCLE("동그라미"),
            PENTAGON("오각형"),
            HEXAGON("육각형"),
            LOZENGE("마름모");

            companion object {
                fun originValueOf(value: String): Figure {
                    return Figure.values().firstOrNull { color -> color.value == value }
                        ?: throw IllegalArgumentException("해당하는 color tag가 없습니다.")
                }
            }
        }
    }
}