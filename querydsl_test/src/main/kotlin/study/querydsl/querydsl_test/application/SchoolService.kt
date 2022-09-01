package study.querydsl.querydsl_test.application

import org.springframework.stereotype.Service
import study.querydsl.querydsl_test.domain.School
import study.querydsl.querydsl_test.domain.SchoolRepository
import javax.transaction.Transactional

@Service
class SchoolService (
    val schoolRepository: SchoolRepository,
) {

    @Transactional
    fun addSchool(name: String, location: String, age: Int) : School {
        val initSchool = School(name, location, age)
        return schoolRepository.save(initSchool)
    }
}