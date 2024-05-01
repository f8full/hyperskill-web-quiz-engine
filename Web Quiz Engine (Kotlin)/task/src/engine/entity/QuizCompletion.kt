package engine.entity

import com.fasterxml.jackson.annotation.JsonFormat
import engine.QuizCompletionIdentifier
import jakarta.persistence.*
import java.time.ZonedDateTime

@Entity
@Table(name = "completion")
data class QuizCompletion(
    @Id
    @GeneratedValue
    val id: QuizCompletionIdentifier? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    var completedAt: ZonedDateTime? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    val quiz: Quiz,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: QuizUser
) {
    @PrePersist
    @PreUpdate
    fun setCompletedAt() {
        completedAt = ZonedDateTime.now()
    }
}
