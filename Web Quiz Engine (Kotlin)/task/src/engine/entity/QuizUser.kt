package engine.entity

import jakarta.persistence.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class QuizUser(
    @Column(name = "user_id")
    @Id @GeneratedValue
    var userId: Int? = null,
    @Column(unique = true)
    @get:JvmName("getUsername_") // to change auto generated getter name and avoid conflict with getUsername method
    var username: String,
    @Column(nullable = false)
    var userPassword: String,
    @Column(nullable = false)
    var authority: String,
) : UserDetails {
    override fun getAuthorities() =  mutableListOf(SimpleGrantedAuthority(authority))

    override fun getPassword() = userPassword

    override fun getUsername() = username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired()= true

    override fun isEnabled() = true
}
