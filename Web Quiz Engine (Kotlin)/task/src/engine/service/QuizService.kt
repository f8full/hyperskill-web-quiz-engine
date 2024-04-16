package engine.service

import engine.WebQuizRepository
import engine.model.QuizPostResponseBody
import engine.model.QuizPostResponseBodyCorrect
import engine.model.QuizPostResponseBodyIncorrect
import engine.exception.QuizNotFoundException
import engine.entity.Quiz
import engine.model.QuizDto
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

    private fun findQuizByIdOrThrow(quizId: Int): Quiz = quizRepository
        .findById(quizId)
        .orElseThrow { QuizNotFoundException(quizId = quizId) }

    fun addQuiz(quizDto: QuizDto): Quiz =
        with(quizDto) {
            quizRepository.save(
                Quiz(
                    title = title,
                    text = text,
                    optionList = optionList,
                    answerList = answerList
                )
            )
        }
}
