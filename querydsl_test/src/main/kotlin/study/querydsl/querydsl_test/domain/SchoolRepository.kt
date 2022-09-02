package study.querydsl.querydsl_test.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface SchoolRepository : JpaRepository<School, Long>, SchoolRepositoryCustom {

    @Query("select new study.querydsl.querydsl_test.domain.SchoolInfo(" +
            "s.id, s.name, s.location, s.age) " +
            "from School s ")
    fun findAllSchools(): List<SchoolInfo>

    fun findAllByOrderByAgeAsc(): List<School>
}