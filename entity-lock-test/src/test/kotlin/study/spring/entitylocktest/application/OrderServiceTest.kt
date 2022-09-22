package study.spring.entitylocktest.application

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import study.spring.entitylocktest.domain.OrderRepository

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
}