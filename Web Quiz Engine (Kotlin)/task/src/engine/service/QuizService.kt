package engine.service

import engine.WebQuizRepository
import engine.model.QuizPostResponseBody
import engine.model.QuizPostResponseBodyCorrect
import engine.model.QuizPostResponseBodyIncorrect
import engine.exception.QuizNotFoundException
import engine.entity.Quiz
import org.springframework.stereotype.Service

@Service
class QuizService(private val quizRepository: WebQuizRepository) {

    fun findQuizById(
        id: Int,
        omitId: Boolean = false,
        omitAnswerList: Boolean = false
        ) =
        quizRepository.getQuizById(
            id = id,
            omitId = omitId,
            omitAnswerList = omitAnswerList,
    )?: throw QuizNotFoundException(quizId = id)

    fun findQuizList(omitAnswerList: Boolean = false) =
        quizRepository.getAllQuizList(omitAnswerList = omitAnswerList)

    fun checkQuizSolution(quizId: Int, answerList: List<Int>?): QuizPostResponseBody =
        quizRepository.getQuizById(id = quizId)?.let { quiz ->
            if ((quiz.answerList.isNullOrEmpty() && answerList.isNullOrEmpty()) ||
                (quiz.answerList?.containsAll(answerList.orEmpty()) == true &&
                        answerList?.containsAll(quiz.answerList) == true))
                QuizPostResponseBodyCorrect()
            else
                QuizPostResponseBodyIncorrect()
        }?: throw QuizNotFoundException(quizId = quizId)

    fun addQuiz(quiz: Quiz) = quizRepository.addQuiz(quiz)
}
