package engine

import engine.entity.Quiz
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

typealias QuizIdentifier = Int

@Repository
interface WebQuizRepository: JpaRepository<Quiz, QuizIdentifier> {
    override fun findAll(pageable: Pageable): Page<Quiz>
}
