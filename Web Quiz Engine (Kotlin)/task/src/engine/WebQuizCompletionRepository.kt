package engine

import engine.entity.QuizCompletion
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

typealias QuizCompletionIdentifier = Int

@Repository
interface WebQuizCompletionRepository: JpaRepository<QuizCompletion, QuizIdentifier> {
    fun findAllByUserUserIdAndQuizIdIsNotNullAndCompletedAtIsNotNull(userId: Int, pageable: Pageable): Page<QuizCompletion>
}
