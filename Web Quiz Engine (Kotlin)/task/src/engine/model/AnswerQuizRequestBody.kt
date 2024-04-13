package engine.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AnswerQuizRequestBody(
    @JsonProperty("answer")
    val answerList: List<Int>?,
    )
