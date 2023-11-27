package com.fiveonetwo.particle.redis.aop

import com.fiveonetwo.particle.redis.annotation.DistributedLock
import com.fiveonetwo.particle.redis.annotation.DistributedLocks
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Service
import kotlin.concurrent.thread

@SpringBootTest
class DistributedLockAOPTest @Autowired constructor(
    private val testService: TestService,
) {
    @Test
    fun `분산락 테스트`() {
        val th1 = thread {
            val result = testService.test("hello1")
            println(result)
        }

        val th2 = thread {
            val result = testService.test("hello1")
            println(result)
        }

        th1.join()
        th2.join()
    }

    @Test
    fun `분산락 다중 어노테이션 테스트`() {
        val th3 = thread {
            val result = testService.test("test key 1")
            println(result)
        }
        val th1 = thread {
            val result = testService.test2("test key 1", "test key 2")
            println(result)
        }
        val th2 = thread {
            val result = testService.test2("test key 1", "test key 2")
            println(result)
        }
        val th4 = thread {
            val result = testService.test2("test key 3", "test key 4")
            println(result)
        }

        th1.join()
        th2.join()
        th3.join()
        th4.join()
    }
}

@Service
class TestService {
    @DistributedLock(key = "#key")
    fun test(key: String): String {
        Thread.sleep(1000L)
        return "test1"
    }

    @DistributedLocks(
        values = [
            DistributedLock(key = "#key1"),
            DistributedLock(key = "#key2")
        ]
    )

    fun test2(key1: String, key2: String): String {
        Thread.sleep(100L)
        return "test2";
    }
}