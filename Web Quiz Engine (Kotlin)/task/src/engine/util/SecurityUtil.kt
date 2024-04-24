package engine.util

import engine.entity.QuizUser
import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtil {
    val authenticatedUser =
        SecurityContextHolder.getContext().authentication.principal as QuizUser
}