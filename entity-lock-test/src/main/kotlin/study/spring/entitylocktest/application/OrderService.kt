package study.spring.entitylocktest.application

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import study.spring.entitylocktest.domain.Order
import study.spring.entitylocktest.domain.OrderRepository

@Service
class OrderService(
    private val repository: OrderRepository
) {

    @Transactional
    fun createOrder(status: String, destination: String, amount: Int): Order {
        val initOrder = Order(status, destination, amount)
        return repository.save(initOrder)
    }

    @Transactional
    fun updateOrderStatus(id: Long, status: String): Order {
        val order = repository.findByIdForUpdate(id) ?: throw IllegalArgumentException("오더 정보가 없습니다. $id")
        order.changeStatus("Delivered")

        return order
    }
}