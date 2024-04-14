package engine.service

import engine.WebQuizRepository
import engine.model.AnswerQuizResponseBody
import engine.model.AnswerQuizResponseBodyCorrect
import engine.model.AnswerQuizResponseBodyIncorrect
import engine.exception.QuizNotFoundException
import engine.entity.Quiz
import org.springframework.stereotype.Service

@Service
class QuizService(
    private val quizRepository: WebQuizRepository,
) {

    fun findQuizById(
        id: Int,
        omitId: Boolean = false,
        omitAnswerList: Boolean = false
        ) =
        quizRepository
            .findById(id)
            .orElseThrow { QuizNotFoundException(quizId = id) }
            .let { quiz ->
                quiz.copy(
                id = if (omitId) null else id,
                answerList = if (omitAnswerList) null else quiz.answerList,
            )
        }

    fun findQuizList(omitAnswerList: Boolean = false) =
        quizRepository.findAll().map { quiz ->
            quiz.copy(
                answerList = if (omitAnswerList) null else quiz.answerList,
            )
        }

    fun checkQuizSolution(quizId: Int, answerList: List<Int>?): AnswerQuizResponseBody =
        quizRepository.findById(quizId)
            .orElseThrow { QuizNotFoundException(quizId = quizId)}
            .let { quiz ->
            if ((quiz.answerList.isNullOrEmpty() && answerList.isNullOrEmpty()) ||
                (quiz.answerList?.containsAll(answerList.orEmpty()) == true &&
                        answerList?.containsAll(quiz.answerList) == true))
                AnswerQuizResponseBodyCorrect()
            else
                AnswerQuizResponseBodyIncorrect()
        }

    fun addQuiz(quiz: Quiz): Quiz =
        quizRepository.save(quiz)
}
