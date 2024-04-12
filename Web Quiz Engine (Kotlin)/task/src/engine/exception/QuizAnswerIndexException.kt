package engine.exception

class QuizAnswerIndexException(private val faultyIndex: Int): RuntimeException() {
    override val message: String
        get() = "The answer index $faultyIndex is out of bounds!"
}