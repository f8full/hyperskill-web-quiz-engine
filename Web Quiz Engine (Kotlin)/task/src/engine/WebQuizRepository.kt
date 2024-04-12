package engine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

typealias QuizIdentifier = Int

@Component
class WebQuizRepository {

    @Autowired
    private lateinit var idGenerator: UniqueIdGenerator

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

    /*fun solveQuiz(id: QuizIdentifier, answer: Answer): Boolean {
        val quiz = quizMap[id] ?: return false
        return quiz.answer == answer
    }*/

    //fun getQuizIds() = quizMap.keys.toList()

    //fun getQuizCount() = quizMap.size
}