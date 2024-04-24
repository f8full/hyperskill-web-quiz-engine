package engine.util

import engine.entity.QuizUser
import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtil {
    fun authenticatedUser() =
        SecurityContextHolder.getContext().authentication.principal as QuizUser
}
