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

    @Version
    var version: Int? = null

    fun changeStatus(newStatus: String) {
        this.status = newStatus
    }

    fun getAmount() = this.amount

    fun getStatus() = this.status

    fun getDestination() = this.destination
}