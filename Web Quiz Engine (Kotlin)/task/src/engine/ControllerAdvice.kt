package engine

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

class QuizAnswerIndexException(private val faultyIndex: Int): RuntimeException() {
    override val message: String
        get() = "The answer index $faultyIndex is out of bounds!"
}

class QuizNotFoundException(private val quizId: Int): RuntimeException() {
    override val message: String
        get() = "Quiz with id $quizId not found!"
}

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingRequestParameterException(e: MissingServletRequestParameterException) =
        ResponseEntity(mapOf("error" to e.message), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(QuizAnswerIndexException::class)
    fun handleInvalidQuizAnswerIndexException(e: QuizAnswerIndexException) =
        ResponseEntity(mapOf("error" to e.message), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(QuizNotFoundException::class)
    fun handleQuizNotFoundException(e: QuizNotFoundException) =
        ResponseEntity(mapOf("error" to e.message), HttpStatus.NOT_FOUND)
}