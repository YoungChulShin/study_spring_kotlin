package study.querydsl.querydsl_test.domain

import javax.persistence.*

@Entity
@Table(name = "school")
class School (
    val name: String,
    val location: String,
    val age: Int,
){

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}