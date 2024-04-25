package engine.exception

import engine.entity.QuizUser

class QuizAuthorMismatchException(
    private val quizAuthor: QuizUser,
    private val quizDeletionRequester: QuizUser,
    private val quizId: Int
): RuntimeException() {
    override val message: String
        get() = "User ${quizDeletionRequester.username} is not the author of the quiz with id $quizId. " +
                "The author of the quiz is ${quizAuthor.username}."
}
