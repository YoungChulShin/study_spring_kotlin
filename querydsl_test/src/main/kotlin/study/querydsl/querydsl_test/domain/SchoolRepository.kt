package study.querydsl.querydsl_test.domain

import org.springframework.data.jpa.repository.JpaRepository

interface SchoolRepository : JpaRepository<School, Long> {
}