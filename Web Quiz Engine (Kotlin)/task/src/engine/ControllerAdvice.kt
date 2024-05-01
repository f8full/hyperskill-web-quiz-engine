package engine

import engine.exception.EmailAlreadyTakenException
import engine.exception.QuizAnswerIndexException
import engine.exception.QuizAuthorMismatchException
import engine.exception.QuizNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(
        MissingServletRequestParameterException::class,
        EmailAlreadyTakenException::class, //This type of error should return HTTP 409 but hyperskill expects 400
        MethodArgumentNotValidException::class,
        HttpMessageNotReadableException::class,
        QuizAnswerIndexException::class
    )
    fun handleBadRequestException(e: Exception) =
        ResponseEntity(mapOf("error" to e.message), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(QuizNotFoundException::class)
    fun handleQuizNotFoundException(e: QuizNotFoundException) =
        ResponseEntity(mapOf("error" to e.message), HttpStatus.NOT_FOUND)

    @ExceptionHandler(QuizAuthorMismatchException::class)
    fun handleQuizAuthorMismatchException(e: QuizAuthorMismatchException) =
        ResponseEntity(mapOf("error" to e.message), HttpStatus.FORBIDDEN)

    @ExceptionHandler(KotlinNullPointerException::class)
    fun handleKotlinNullPointerException(e: KotlinNullPointerException) =
        ResponseEntity(mapOf("error" to e.message), HttpStatus.INTERNAL_SERVER_ERROR)
}
