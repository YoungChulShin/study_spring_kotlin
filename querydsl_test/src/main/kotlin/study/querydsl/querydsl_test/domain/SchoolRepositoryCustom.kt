package study.querydsl.querydsl_test.domain

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

interface SchoolRepositoryCustom {

    fun findSchoolWithName(name: String): List<School>

    fun findSchoolWithLocationAndAge(location: String, age: Int): List<School>

    fun findAllSchoolsByQuerydsl(): List<SchoolInfo>

    fun findAllSchoolOrderByAgeByQuerydsl(): List<School>

    fun findAllSchoolWithPaging(pageRequest: PageRequest): Page<School>

    fun findAllSchoolWithPaging2(pageRequest: PageRequest): List<School>
}