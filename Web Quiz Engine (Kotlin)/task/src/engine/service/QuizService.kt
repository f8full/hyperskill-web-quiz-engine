package engine.service

import engine.WebQuizCompletionRepository
import engine.WebQuizRepository
import engine.exception.QuizNotFoundException
import engine.entity.Quiz
import engine.entity.QuizCompletion
import engine.exception.QuizAuthorMismatchException
import engine.model.*
import engine.util.SecurityUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.ZonedDateTime

@Service
class QuizService(
    private val quizRepository: WebQuizRepository,
    private val quizCompletionRepository: WebQuizCompletionRepository,
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
            if (author.userId != SecurityUtil.authenticatedUser().userId) {
                throw QuizAuthorMismatchException(
                    quizAuthor = author,
                    quizDeletionRequester = SecurityUtil.authenticatedUser(),
                    quizId = quizId
                )
            }

            quizRepository.delete(this)
        }

    fun findQuizPage(pageable: Pageable): Page<QuizDto> =
        quizRepository.findAll(pageable = pageable).map { quiz ->
            with(quiz) {
                QuizDto(
                    id = id,
                    title = title,
                    text = text,
                    optionList = optionList,
                )
            }
        }

    fun findCompletedQuizPageForCurrentUser(pageable: Pageable): Page<QuizCompletionDto> =
        quizCompletionRepository.findAllByUserUserIdAndQuizIdIsNotNullAndCompletedAtIsNotNull(
            userId = SecurityUtil.authenticatedUser().userId!!,
            pageable = pageable,
            ).map { quizCompletion ->
            with(quizCompletion) {
                QuizCompletionDto(
                    id = quiz.id!!, // nullable id is filtered out by the database query
                    completedAt = completedAt!! //same
                )
            }
        }

    fun checkQuizSolution(quizId: Int, answerList: List<Int>?): QuizPostResponseBody =
        findQuizByIdOrThrow(quizId)
            .let { quiz ->
            if ((quiz.answerList.isNullOrEmpty() && answerList.isNullOrEmpty()) ||
                quiz.answerList?.size == answerList?.size &&
                (quiz.answerList?.containsAll(answerList.orEmpty()) == true &&
                        answerList?.containsAll(quiz.answerList) == true)) {
                quizCompletionRepository.save(
                    QuizCompletion(
                        quiz = quiz,
                        user = SecurityUtil.authenticatedUser()
                    )
                )
                QuizPostResponseBodyCorrect()
            }
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
                    author = SecurityUtil.authenticatedUser()
                )
            )
        }

    private fun findQuizByIdOrThrow(quizId: Int): Quiz = quizRepository
        .findById(quizId)
        .orElseThrow { QuizNotFoundException(quizId = quizId) }
}
