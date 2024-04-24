package engine.service

import engine.QuizUserRepository
import engine.entity.QuizUser
import engine.exception.EmailAlreadyTakenException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class QuizUserDetailsService(
    private val quizUserRepository: QuizUserRepository,
    ): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails =
        with(quizUserRepository.findQuizUserByUsername(username)?:
        throw UsernameNotFoundException("User not found: $username")) {
            this
        }

    fun registerUser(email: String, password: String) {
        quizUserRepository.findQuizUserByUsername(email)?.let {
            throw EmailAlreadyTakenException(email = email)
        }

        val user = QuizUser(
            username = email,
            userPassword = BCryptPasswordEncoder().encode(password),
            authority = "USER"
        )

        quizUserRepository.save(user)
    }
}
