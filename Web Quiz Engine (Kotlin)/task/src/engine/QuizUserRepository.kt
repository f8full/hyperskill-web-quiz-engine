package engine

import engine.entity.QuizUser
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface QuizUserRepository: CrudRepository<QuizUser, Int> {
    fun findQuizUserByUsername(username: String): QuizUser?
}
