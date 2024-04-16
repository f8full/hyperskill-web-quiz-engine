package engine

import engine.entity.Quiz
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

typealias QuizIdentifier = Int

@Repository
interface WebQuizRepository: CrudRepository<Quiz, QuizIdentifier>
