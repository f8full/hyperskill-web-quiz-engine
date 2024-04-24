package engine.model

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class RegisterUserRequestBody(
    @field:Pattern(regexp = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)*(\\.[a-zA-Z]{2,})$", message = "Invalid email format")
    val email: String,
    @field:Size(min = 5, message = "The password must be at least 5 characters long")
    val password: String,
    )
