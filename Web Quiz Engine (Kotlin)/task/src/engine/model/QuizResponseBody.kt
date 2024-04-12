package engine.model

sealed class QuizPostResponseBody(val success: Boolean, val feedback: String)
class QuizPostResponseBodyCorrect(success: Boolean = true, feedback: String = "Congratulations, you're right!")
    : QuizPostResponseBody(success, feedback)
class QuizPostResponseBodyIncorrect(success: Boolean = false, feedback: String = "Wrong answer! Please, try again.")
    : QuizPostResponseBody(success, feedback)
