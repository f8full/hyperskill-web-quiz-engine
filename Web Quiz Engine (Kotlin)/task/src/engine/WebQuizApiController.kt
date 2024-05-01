package engine

import engine.entity.Quiz
import engine.model.*
import engine.service.QuizService
import engine.service.QuizUserDetailsService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class WebQuizApiController(
    private val quizService: QuizService,
    private val userService: QuizUserDetailsService,
    ) {

    @PostMapping("/register")
    //@ResponseStatus(HttpStatus.CREATED) should be 201 but hyperskill expects 200
    fun register(@Valid @RequestBody request: RegisterUserRequestBody): String {
        userService.registerUser(
            email = request.email,
            password = request.password
        )

        return "New user successfully registered"
    }

    @PostMapping("/quizzes/{quizId}/solve")
    @ResponseStatus(HttpStatus.OK)
    fun answerQuiz(@RequestBody reqBody: AnswerQuizRequestBody, @PathVariable quizId: Int): QuizPostResponseBody =
        quizService.checkQuizSolution(
            quizId = quizId,
            answerList = reqBody.answerList,
        )
    @PostMapping("/quizzes")
    @ResponseStatus(HttpStatus.OK)
    fun addQuiz(@Valid @RequestBody quizDto: QuizDto): Quiz =
        quizService.addQuiz( quizDto = quizDto)

    @DeleteMapping("/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteQuizById(@PathVariable(name = "id") quizId: Int) =
        quizService.deleteQuizById(quizId)

    @GetMapping("/quizzes/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getQuizById(@PathVariable(name = "id") quizId: Int): QuizDto =
        quizService.findQuizById(quizId)

    @GetMapping("/quizzes")
    @ResponseStatus(HttpStatus.OK)
    fun getQuizPage(@PageableDefault(page = 0, size = 10) pageable: Pageable
    ): Page<QuizDto> =
        quizService.findQuizPage(pageable = pageable)

    @GetMapping("/quizzes/completed")
    @ResponseStatus(HttpStatus.OK)
    fun getCompletedQuizList(@PageableDefault(page = 0, size = 10) pageable: Pageable
    ): Page<QuizCompletionDto> =
        quizService.findCompletedQuizPageForCurrentUser(pageable = pageable)
}
