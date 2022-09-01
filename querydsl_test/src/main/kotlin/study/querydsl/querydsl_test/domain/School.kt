package study.querydsl.querydsl_test.domain

import javax.persistence.*

@Entity
@Table(name = "school")
class School (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?
        ){
}