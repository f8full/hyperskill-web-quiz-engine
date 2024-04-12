package engine.service

import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicInteger

@Service
class UniqueIdGenerator {
    private val currentId = AtomicInteger(0)

    fun getNextId(): Int {
        return currentId.incrementAndGet()
    }
}
