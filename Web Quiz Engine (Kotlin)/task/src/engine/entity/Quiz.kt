package engine.entity

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import engine.QuizIdentifier
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "quiz")
data class Quiz(
    @Id
    @GeneratedValue
    val id: QuizIdentifier? = null,
    val title: String,
    val text: String,
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "option_list")
    val optionList: List<String>,
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "answer_list")
    val answerList: List<Int>? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val author: QuizUser
    )
