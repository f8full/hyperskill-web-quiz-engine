package engine.model

sealed class AnswerQuizResponseBody(val success: Boolean, val feedback: String)
class AnswerQuizResponseBodyCorrect(success: Boolean = true, feedback: String = "Congratulations, you're right!")
    : AnswerQuizResponseBody(success, feedback)
class AnswerQuizResponseBodyIncorrect(success: Boolean = false, feedback: String = "Wrong answer! Please, try again.")
    : AnswerQuizResponseBody(success, feedback)
