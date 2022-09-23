package study.spring.entitylocktest.application

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import study.spring.entitylocktest.domain.OrderRepository
import java.util.concurrent.CompletableFuture

@SpringBootTest
internal class OrderServiceTest {

    @Autowired
    private lateinit var orderService: OrderService

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Test
    fun `오더 생성`() {
        // given
        val status = "Created"
        val destination = "서울시 강동구 상암로"
        val amount = 10000

        // when
        val order = orderService.createOrder(status, destination, amount)

        // then
        val findOrder = orderRepository.findById(order.id!!)
        Assertions.assertThat(findOrder).isPresent
        Assertions.assertThat(findOrder.get().getDestination()).isEqualTo(destination)
        Assertions.assertThat(findOrder.get().getStatus()).isEqualTo(status)
        Assertions.assertThat(findOrder.get().getAmount()).isEqualTo(amount)
    }

    @Test
    fun `오더 상태 업데이트`() {
        // given
        val status = "Created"
        val destination = "서울시 강동구 상암로"
        val amount = 10000
        val order = orderService.createOrder(status, destination, amount)

        // when
        val updateOrder = orderService.updateOrderStatus(order.id!!, "Delivered")

        // then
        Assertions.assertThat(updateOrder.getStatus()).isEqualTo("Delivered")
    }

    @Test
    fun `오더 선점잠금 테스트`() {
        // given
        val status = "Created"
        val destination = "서울시 강동구 상암로"
        val amount = 10000
        val order = orderService.createOrder(status, destination, amount)

        // when
        val supplyAsync1 = CompletableFuture.supplyAsync {
            orderService.updateOrderStatusWithLongTask(
                order.id!!,
                "Delivered"
            )
        }
        val supplyAsync2 = CompletableFuture.supplyAsync {
            orderService.updateOrderStatusWithLongTask(
                order.id!!,
                "Cancelled"
            )
        }

        CompletableFuture.allOf(supplyAsync1, supplyAsync2).get()


        // then
    }
}