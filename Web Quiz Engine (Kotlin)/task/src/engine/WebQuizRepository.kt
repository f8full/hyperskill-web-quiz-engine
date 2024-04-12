package engine

import engine.entity.Quiz
import engine.service.UniqueIdGenerator
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

typealias QuizIdentifier = Int

@Repository
class WebQuizRepository(private val idGenerator: UniqueIdGenerator) {

    private val quizMap: ConcurrentMap<QuizIdentifier, Quiz> = ConcurrentHashMap()

    fun addQuiz(quiz: Quiz): Quiz {
        val id = idGenerator.getNextId()
        quizMap[id] = quiz
        return quiz.copy(id = id)
    }

    fun getQuizById(
        id: QuizIdentifier,
        omitId: Boolean = false,
        omitAnswerList: Boolean = false
        ): Quiz? =
        quizMap[id]?.let { quiz ->
            quiz.copy(
                id = if (omitId) null else id,
                answerList = if(omitAnswerList) null else quiz.answerList
            )
        }

    fun getAllQuizList(omitAnswerList: Boolean = false): List<Quiz> = quizMap.map { (id, quiz) ->
        quiz.copy(id = id, answerList = if (omitAnswerList) null else quiz.answerList)
    }

    fun deleteQuizById(id: QuizIdentifier): Boolean = quizMap.remove(id) != null

    fun deleteAllQuizzes() = quizMap.clear()
}
