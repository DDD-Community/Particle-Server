package com.fiveonetwo.particle.domain.user.entity

import com.fiveonetwo.particle.domain.common.entity.BaseTimeEntity
import com.fiveonetwo.particle.domain.record.entity.RecordTagValue
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
    val profileImageUrl: String = "https://ddd-particle-bucket.s3.ap-northeast-2.amazonaws.com/default_image.png",
    @Column(name = "recommend_tags", nullable = false)
    val interestedTags: MutableList<RecordTagValue> = mutableListOf(),
) : BaseTimeEntity()

fun createRandomUsername(): String {
    val colors = Color.values()
    val figures = Figure.values()
    return "${colors[Random.nextInt(0..colors.lastIndex)].value} ${figures[Random.nextInt(0..figures.lastIndex)].value}"
}

class UsernameContext {
    companion object {
        enum class Color(val value: String) {
            RED("붉은"),
            YELLOW("노란"),
            ORANGE("오렌지"),
            GREEN("초록"),
            BLUE("파아란"),
            PURPLE("보랏빛"),
            PINK("핑크"),
            MAGENTA("마젠타"),
            CYAN("시안"),
        }

        enum class Figure(val value: String) {
            ISOSCELES_TRIANGLE("이등변삼각형"),
            RIGHT_TRIANGLE("직각삼각형"),
            EQUILATERAL_TRIANGLE("정삼각형"),
            SQUARE("정사각형"),
            ELLIPSE("타원"),
            CIRCLE("동그라미"),
            PARALLELOGRAM("평행사변형"),
            TRAPEZOID("사다리꼴"),
            RECTANGLE("직사각형"),
            RHOMBUS("마름모"),
        }
    }
}