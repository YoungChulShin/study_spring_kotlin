package study.spring.entitylocktest.domain

import javax.persistence.*

@Entity
@Table(name = "orders")
class Order(
    private var status: String,
    private var destination: String,
    private var amount: Int,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    fun changeStatus(newStatus: String) {
        this.status = newStatus
    }
}