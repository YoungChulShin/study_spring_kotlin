package study.spring.entitylocktest.application

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import study.spring.entitylocktest.domain.Order
import study.spring.entitylocktest.domain.OrderRepository

@Service
class OrderService(
    private val repository: OrderRepository
) {
    private val logger: Logger = LoggerFactory.getLogger(OrderService::class.java)

    @Transactional
    fun createOrder(status: String, destination: String, amount: Int): Order {
        val initOrder = Order(status, destination, amount)
        return repository.save(initOrder)
    }

    @Transactional
    fun updateOrderStatus(id: Long, status: String): Order {
        val order = repository.findByIdForUpdate(id) ?: throw IllegalArgumentException("오더 정보가 없습니다. $id")
        order.changeStatus(status)

        return order
    }

    @Transactional
    fun updateOrderStatusWithLongTask(id: Long, status: String): Order {
        val currentThread = Thread.currentThread()
        logger.info("${currentThread.name} - 오더 조회 시작")
        val order = repository.findByIdForUpdate(id) ?: throw IllegalArgumentException("오더 정보가 없습니다. $id")
        logger.info("${currentThread.name} - 오더 조회 완료")
        logger.info("${currentThread.name} - 오더 업데이트 시작")
        order.changeStatus(status)
        Thread.sleep(1000)
        logger.info("${currentThread.name} - 오더 업데이트 완료")

        return order
    }

    @Transactional
    fun updateOrderStatusWithoutLock(id: Long, status: String): Order {
        val currentThread = Thread.currentThread()
        logger.info("${currentThread.name} - 오더 조회 시작")
        val order = repository.findByIdOrNull(id) ?: throw IllegalArgumentException("오더 정보가 없습니다. $id")
        logger.info("${currentThread.name} - 오더 조회 완료")
        logger.info("${currentThread.name} - 오더 업데이트 시작")
        order.changeStatus(status)
        Thread.sleep(1000)
        logger.info("${currentThread.name} - 오더 업데이트 완료")

        return order
    }
}