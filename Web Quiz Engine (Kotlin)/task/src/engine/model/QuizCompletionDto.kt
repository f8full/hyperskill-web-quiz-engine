package engine.model

import com.fasterxml.jackson.annotation.JsonFormat
import engine.QuizIdentifier
import java.time.ZonedDateTime

data class QuizCompletionDto(
    val id: QuizIdentifier,
    @field:JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    val completedAt: ZonedDateTime
)
