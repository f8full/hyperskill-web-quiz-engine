package engine.service

import engine.WebQuizRepository
import engine.model.QuizPostResponseBody
import engine.model.QuizPostResponseBodyCorrect
import engine.model.QuizPostResponseBodyIncorrect
import engine.exception.QuizNotFoundException
import engine.entity.Quiz
import engine.exception.QuizAuthorMismatchException
import engine.model.QuizDto
import engine.util.SecurityUtil
import org.springframework.stereotype.Service

@Service
class QuizService(
    private val quizRepository: WebQuizRepository,
) {

    fun findQuizById(id: Int): QuizDto =
        with(findQuizByIdOrThrow(quizId = id)) {
            QuizDto(
                id = id,
                title = title,
                text = text,
                optionList = optionList,
            )
        }

    fun deleteQuizById(quizId: Int): Unit =
        with(findQuizByIdOrThrow(quizId = quizId)) {
            if (author.userId != SecurityUtil.authenticatedUser.userId) {
                throw QuizAuthorMismatchException(
                    quizAuthor = author,
                    quizDeletionRequester = SecurityUtil.authenticatedUser,
                    quizId = quizId
                )
            }

            quizRepository.delete(this)
        }

    fun findQuizList(): List<QuizDto> =
        quizRepository.findAll().map { quiz ->
            with(quiz) {
                QuizDto(
                    id = id,
                    title = title,
                    text = text,
                    optionList = optionList,
                )
            }
        }

    fun checkQuizSolution(quizId: Int, answerList: List<Int>?): QuizPostResponseBody =
        findQuizByIdOrThrow(quizId)
            .let { quiz ->
            if ((quiz.answerList.isNullOrEmpty() && answerList.isNullOrEmpty()) ||
                quiz.answerList?.size == answerList?.size &&
                (quiz.answerList?.containsAll(answerList.orEmpty()) == true &&
                        answerList?.containsAll(quiz.answerList) == true))
                QuizPostResponseBodyCorrect()
            else
                QuizPostResponseBodyIncorrect()
        }

    fun addQuiz(quizDto: QuizDto): Quiz =
        with(quizDto) {
            quizRepository.save(
                Quiz(
                    title = title,
                    text = text,
                    optionList = optionList,
                    answerList = answerList,
                    author = SecurityUtil.authenticatedUser
                )
            )
        }

    private fun findQuizByIdOrThrow(quizId: Int): Quiz = quizRepository
        .findById(quizId)
        .orElseThrow { QuizNotFoundException(quizId = quizId) }
}
