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
        quizRepository
            .findById(id)
            .orElseThrow { QuizNotFoundException(quizId = id) }
            .let {
                with(it) {
                    QuizDto(
                        id = id,
                        title = title,
                        text = text,
                        optionList = optionList,
                    )
                }
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
        quizRepository.findById(quizId)
            .orElseThrow { QuizNotFoundException(quizId = quizId)}
            .let { quiz ->
            if ((quiz.answerList.isNullOrEmpty() && answerList.isNullOrEmpty()) ||
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
                    answerList = answerList
                )
            )
        }
}
