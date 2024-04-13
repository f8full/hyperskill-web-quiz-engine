package engine.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import engine.QuizIdentifier
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@JsonInclude(JsonInclude.Include.NON_NULL)
data class QuizDto(
    val id: QuizIdentifier? = null,
    @field:NotBlank(message = "The quiz must have a title")
    val title: String,
    @field:NotBlank(message = "The quiz must have a text")
    val text: String,
    @field:Size(min = 2, message = "The quiz must have at least 2 options")
    @JsonProperty("options")
    val optionList: List<String>,
    @JsonProperty("answer")
    val answerList: List<Int>? = null,
    )
