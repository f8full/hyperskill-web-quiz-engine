package engine

import engine.model.AnswerQuizRequestBody
import engine.entity.Quiz
import engine.model.QuizDto
import engine.model.QuizPostResponseBody
import engine.service.QuizService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class WebQuizApiController(private val quizService: QuizService) {

    @PostMapping("/api/quizzes/{quizId}/solve")
    @ResponseStatus(HttpStatus.OK)
    fun answerQuiz(@RequestBody reqBody: AnswerQuizRequestBody, @PathVariable quizId: Int): QuizPostResponseBody =
        quizService.checkQuizSolution(
            quizId = quizId,
            answerList = reqBody.answerList,
        )
    @PostMapping("/api/quizzes")
    @ResponseStatus(HttpStatus.OK)
    fun addQuiz(@Valid @RequestBody quizDto: QuizDto): Quiz =
        quizService.addQuiz( quizDto = quizDto)

    @GetMapping("/api/quizzes/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getQuizById(@PathVariable(name = "id") quizId: Int): QuizDto =
        quizService.findQuizById(quizId)

    @GetMapping("/api/quizzes")
    @ResponseStatus(HttpStatus.OK)
    fun getQuizList(): List<QuizDto> =
        quizService.findQuizList()
}
