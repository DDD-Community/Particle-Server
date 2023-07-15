package com.fiveonetwo.particle.domain.user.entity

import com.fiveonetwo.particle.shared.utils.uuid
import org.springframework.data.annotation.Id
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "users")
class User(
    @Id
    @Column("user_id")
    private var id: String = "-1",
    val provider: String,
    val identifier: String,
    val nickname : String,
) : Persistable<String> {
    override fun getId(): String = id
    override fun isNew(): Boolean {
        val result = ("-1" == id)
        id = uuid()
        return result
    }
}