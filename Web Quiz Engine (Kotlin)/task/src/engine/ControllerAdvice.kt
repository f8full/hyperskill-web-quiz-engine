package engine

import engine.exception.QuizAnswerIndexException
import engine.exception.QuizNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(MissingServletRequestParameterException::class, QuizAnswerIndexException::class)
    fun handleException(e: Exception) =
        ResponseEntity(mapOf("error" to e.message), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(QuizNotFoundException::class)
    fun handleQuizNotFoundException(e: QuizNotFoundException) =
        ResponseEntity(mapOf("error" to e.message), HttpStatus.NOT_FOUND)
}
