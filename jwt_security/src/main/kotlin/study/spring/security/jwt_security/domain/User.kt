package study.spring.security.jwt_security.domain

import javax.persistence.*

@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name")
    var name: String,

    @Column(name = "username")
    val username: String,

    @Column(name = "password")
    var password: String,

    @ManyToMany(fetch = FetchType.EAGER)
    val roles: MutableList<Role> = mutableListOf()
)