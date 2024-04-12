package engine.exception

class QuizNotFoundException(private val quizId: Int): RuntimeException() {
    override val message: String
        get() = "Quiz with id $quizId not found!"
}
