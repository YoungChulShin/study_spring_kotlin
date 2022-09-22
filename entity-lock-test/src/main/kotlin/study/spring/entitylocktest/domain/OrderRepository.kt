package study.spring.entitylocktest.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import javax.persistence.LockModeType

interface OrderRepository : JpaRepository<Order, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from Order o where o.id = :id")
    fun findByIdForUpdate(@Param("id") id: Long): Order
}