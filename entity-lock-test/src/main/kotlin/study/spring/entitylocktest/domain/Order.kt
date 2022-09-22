package study.spring.entitylocktest.domain

import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    var status: String,
    var destination: String,
    var amount: Int,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}