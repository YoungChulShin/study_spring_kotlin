package study.spring.restdocstest.domain

import javax.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name")
    val name: String,

    @Column(name = "age")
    val age: Int,

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    val gender: Gender,
) {

    constructor(name: String, age: Int, gender: Gender) : this(null, name, age, gender)

}