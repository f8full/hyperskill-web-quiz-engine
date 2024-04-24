package engine.exception

class EmailAlreadyTakenException(private val email: String): RuntimeException() {
    override val message: String
        get() = "Email $email already taken"
}
