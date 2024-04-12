package engine

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


// exception dans leur propre package
// pas de logique dans POJO
// chaque POJO dans son ficher dans un package model ?
// inclue repsonse et request body
// pas de logique dans le controller


data class AnswerQuizRequestBody(val answer: List<Int>?)
@RestController
class WebQuizApiController {

    @Autowired
    private lateinit var quizRepository: WebQuizRepository

    sealed class QuizPostResponseBody(val success: Boolean, val feedback: String)
    class QuizPostResponseBodyCorrect(success: Boolean = true, feedback: String = "Congratulations, you're right!")
        : QuizPostResponseBody(success, feedback)
    class QuizPostResponseBodyIncorrect(success: Boolean = false, feedback: String = "Wrong answer! Please, try again.")
        : QuizPostResponseBody(success, feedback)

    @PostMapping("/api/quizzes/{quizId}/solve")
    fun answerQuiz(@RequestBody reqBody: AnswerQuizRequestBody, @PathVariable quizId: Int):
            ResponseEntity<QuizPostResponseBody> =
        quizRepository.getQuizById(id = quizId)?.let { quiz ->
            ResponseEntity(
                if ((quiz.answerList.isNullOrEmpty() && reqBody.answer.isNullOrEmpty()) ||
                    (quiz.answerList?.containsAll(reqBody.answer.orEmpty()) == true &&
                            reqBody.answer?.containsAll(quiz.answerList) == true))
                    QuizPostResponseBodyCorrect()
                else
                    QuizPostResponseBodyIncorrect(),
                HttpStatus.OK,
            )
        } ?: throw QuizNotFoundException(quizId)

    @PostMapping("/api/quizzes")
    fun addQuiz(@Valid @RequestBody quiz: Quiz): ResponseEntity<Quiz> =
        ResponseEntity(
            quizRepository.addQuiz(quiz),
            HttpStatus.OK,
        ).apply {
            println("Quiz added: $quiz")
        }

    @GetMapping("/api/quizzes/{id}")
    fun getQuizById(@PathVariable(name = "id") quizId: Int): ResponseEntity<Quiz> =
        quizRepository.getQuizById(quizId)?.let {
            ResponseEntity(
                quizRepository.getQuizById(quizId, omitAnswerList = true),
                HttpStatus.OK,
            )
        }?: throw QuizNotFoundException(quizId)

    @GetMapping("/api/quizzes")
    fun getQuizList(): ResponseEntity<List<Quiz>> =
            ResponseEntity(
                quizRepository.getAllQuizList(omitAnswerList = true),
                HttpStatus.OK,
            )
}